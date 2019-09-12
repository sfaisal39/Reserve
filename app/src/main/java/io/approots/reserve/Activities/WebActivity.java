package io.approots.reserve.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.http.SslCertificate;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.io.ByteArrayInputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import io.approots.reserve.CustomFont.RegularTextView;
import io.approots.reserve.R;

public class WebActivity extends AppCompatActivity {

    WebView myWebView;
    String myUrl;
    ProgressBar progressBar;
    RegularTextView titletxt;
    RelativeLayout button;

    String TAG = "TAGWEB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web);
        titletxt = (RegularTextView) findViewById(R.id.titletxt);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        myWebView = (WebView) findViewById(R.id.webView_link);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new MyWebViewClient());
        myWebView.getSettings().getLoadsImagesAutomatically();
        myWebView.getProgress();
        myWebView.getSettings().getUseWideViewPort();
        myWebView.getSettings().supportMultipleWindows();


        Intent intent = getIntent();
        Bundle args = intent.getExtras();
        if (args != null) {
            if (args.containsKey("URL")) {


                Log.v("URL", args.getString("URL"));
                myUrl = args.getString("URL");
                if (!myUrl.startsWith("www.") && !myUrl.startsWith("http://")) {
                    myUrl = "www." + myUrl;
                }
                if (!myUrl.startsWith("http://")) {
                    myUrl = "http://" + myUrl;
                }

                titletxt.setText(myUrl);

                myWebView.loadUrl(myUrl);
            }
        }

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            myUrl = url;
            view.loadUrl(myUrl);
            progressBar.setVisibility(View.VISIBLE);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progressBar.setVisibility(View.GONE);
            super.onPageFinished(view, url);

            System.out.println("onPageFinished: " + url);
            if ("about:blank".equals(url) && view.getTag() != null) {
                view.loadUrl(myUrl);
            } else {
                view.setTag(url);
            }

        }

        @Override
        public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail) {
            progressBar.setVisibility(View.GONE);
            return super.onRenderProcessGone(view, detail);


        }

        @Override
        public void onPageCommitVisible(WebView view, String url) {
            super.onPageCommitVisible(view, url);
            titletxt.setText(url);
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
//            handler.proceed(); // Ignore SSL certificate errors
            final AlertDialog.Builder builder = new AlertDialog.Builder(WebActivity.this);
            builder.setMessage(R.string.notification_error_ssl_cert_invalid);
            builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.proceed();
                }
            });
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.cancel();
                }
            });
            final AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

//        @Override
//        public void onReceivedSslError(WebView v, final SslErrorHandler handler, SslError er){
//            // first check certificate with our truststore
//            // if not trusted, show dialog to user
//            // if trusted, proceed
//            try {
//                TrustManagerFactory tmf = TrustManagerUtil.getTrustManagerFactory(resources);
//
//                for(TrustManager t: tmf.getTrustManagers()){
//                    if (t instanceof X509TrustManager) {
//
//                        X509TrustManager trustManager = (X509TrustManager) t;
//
//                        Bundle bundle = SslCertificate.saveState(er.getCertificate());
//                        X509Certificate x509Certificate;
//                        byte[] bytes = bundle.getByteArray("x509-certificate");
//                        if (bytes == null) {
//                            x509Certificate = null;
//                        } else {
//                            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
//                            Certificate cert = certFactory.generateCertificate(new ByteArrayInputStream(bytes));
//                            x509Certificate = (X509Certificate) cert;
//                        }
//                        X509Certificate[] x509Certificates = new X509Certificate[1];
//                        x509Certificates[0] = x509Certificate;
//
//                        trustManager.checkServerTrusted(x509Certificates, "ECDH_RSA");
//                    }
//                }
//                Log.d(TAG, "Certificate from " + er.getUrl() + " is trusted.");
//                handler.proceed();
//            }catch(Exception e){
//                Log.d(TAG, "Failed to access " + er.getUrl() + ". Error: " + er.getPrimaryError());
//                final AlertDialog.Builder builder = new AlertDialog.Builder(WebActivity.this);
//                String message = "SSL Certificate error.";
//                switch (er.getPrimaryError()) {
//                    case SslError.SSL_UNTRUSTED:
//                        message = "O certificado não é confiável.";
//                        break;
//                    case SslError.SSL_EXPIRED:
//                        message = "O certificado expirou.";
//                        break;
//                    case SslError.SSL_IDMISMATCH:
//                        message = "Hostname inválido para o certificado.";
//                        break;
//                    case SslError.SSL_NOTYETVALID:
//                        message = "O certificado é inválido.";
//                        break;
//                }
//                message += " Deseja continuar mesmo assim?";
//
//                builder.setTitle("Erro");
//                builder.setMessage(message);
//                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        handler.proceed();
//                    }
//                });
//                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        handler.cancel();
//                    }
//                });
//                final AlertDialog dialog = builder.create();
//                dialog.show();
//            }


    public void newclick(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (myWebView.canGoBack()) {
                        myWebView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
