package com.jamesmwangi.keddit


import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jamesmwangi.keddit.adapter.NewsAdapter
import com.jamesmwangi.keddit.commons.InfiniteScrollListener
import com.jamesmwangi.keddit.commons.RxBaseFragment
import com.jamesmwangi.keddit.commons.extensions.inflate
import com.jamesmwangi.keddit.commons.models.RedditNews
import kotlinx.android.synthetic.main.news_fragment.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class NewsFragment : RxBaseFragment() {

    companion object{
        private val KEY_REDDIT_NEWS  = "redditNews"
    }

    @Inject lateinit var newsManager: NewsManager

    private val newList by lazy {
        news_list
    }

    private var redditNews: RedditNews? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KedditApp.newsComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return container?.inflate(R.layout.news_fragment)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        news_list.apply {
            setHasFixedSize(true)
            val linearLayout  = LinearLayoutManager(context)
            layoutManager = linearLayout
            clearOnScrollListeners()
            addOnScrollListener(InfiniteScrollListener({requestNews()}, linearLayout))
        }

        initAdapter()

        if(savedInstanceState != null && savedInstanceState.containsKey(KEY_REDDIT_NEWS)){
            redditNews = savedInstanceState.get(KEY_REDDIT_NEWS)as RedditNews
            (news_list.adapter as NewsAdapter).clearAndAddNews(redditNews!!.news)
        } else {
            requestNews()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val news = (news_list.adapter as NewsAdapter).getNews()
        if(redditNews != null && news.size > 0){
            outState.putParcelable(KEY_REDDIT_NEWS,  redditNews?.copy(news = news))
        }
    }

    private fun requestNews() {
        val subscription = newsManager.getNews(redditNews?.after ?: "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {retrievedNews ->
                            redditNews = retrievedNews
                            (news_list.adapter as NewsAdapter).addNews(retrievedNews.news)},
                        {e -> Snackbar.make(news_list, e.message ?: "", Snackbar.LENGTH_LONG).show()}
                )
        subscriptions.add(subscription)

    }

    private fun initAdapter() {
        if(news_list.adapter == null){
            news_list.adapter = NewsAdapter()
        }
    }

}