package com.sjl.exercise.thread.AsyncTask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.sjl.exercise.R;
import com.sjl.exercise.base.BaseActivity;

import java.lang.ref.WeakReference;

public class AsyncTaskActivity extends BaseActivity {
    private static final String TAG = "AsyncTaskActivity";

    @Override
    public int getContentLayout() {
        return R.layout.activity_async_task;
    }

    private TextView tvResult;

    private MyAsyncTask myAsyncTask;

    @Override
    public void initView() {
        tvResult = findViewById(R.id.tv_result);

        findViewById(R.id.btn_start).setOnClickListener(v -> {
            //手动调用且必须在UI线程调用，同一AsyncTask对象只能执行一次
            myAsyncTask.execute("传入参数123");
        });
        findViewById(R.id.btn_cancel).setOnClickListener(v -> myAsyncTask.cancel(true));
    }

    @Override
    public void initData(Bundle bundle) {
        myAsyncTask = new MyAsyncTask(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myAsyncTask.cancel(true);
    }

    static class MyAsyncTask extends AsyncTask<String, Integer, String> {
        private WeakReference<AsyncTaskActivity> weakReference;

        public MyAsyncTask(AsyncTaskActivity asyncTaskActivity) {
            this.weakReference = new WeakReference<>(asyncTaskActivity);
        }

        /**
         * 执行线程任务前操作
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (weakReference.get() != null) {
                weakReference.get().tvResult.setText("AsyncTask执行前");
            }
            Log.i(TAG, "onPreExecute");
        }

        /**
         * 接收输入参数，执行任务中耗时操作，返回线程任务执行结果
         *
         * @param strings
         * @return
         */
        @Override
        protected String doInBackground(String... strings) {
            Log.i(TAG, "doInBackground:" + strings[0]);
            try {
                int count = 0;
                while (count < 100) {
                    count++;
                    publishProgress(count);
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "finish";
        }

        /**
         * 主线程显示任务执行进度
         *
         * @param values
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i(TAG, "onProgressUpdate:"+ values[0]);
            super.onProgressUpdate(values);
            if (weakReference.get() != null) {
                weakReference.get().tvResult.setText("当前进度：" + values[0]);
            }
        }

        /**
         * 接收线程任务执行结果
         *
         * @param s
         */
        @Override
        protected void onPostExecute(String s) {
            Log.i(TAG, "onPostExecute");
            super.onPostExecute(s);
            if (weakReference.get() != null) {
                weakReference.get().tvResult.setText("执行结束：" + s);
            }
        }

        /**
         * 任务状态设置成取消状态
         */
        @Override
        protected void onCancelled() {
            Log.i(TAG, "onCancelled");
            super.onCancelled();
            if (weakReference.get() != null) {
                weakReference.get().tvResult.setText("任务取消");
            }
        }
    }
}
