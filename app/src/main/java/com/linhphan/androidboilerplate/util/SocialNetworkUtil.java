package com.linhphan.androidboilerplate.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

/**
 * Created by linhphan on 11/11/15.
 */
public class SocialNetworkUtil {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////                                               ////////////////////////
    /////////////////////////            Facebook                           ////////////////////////
    /////////////////////////                                               ////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * share an image to facebook
     *
     * @param callbackManager facebook callback manager
     * @param uri             the uri of image
     */
//    private void shareToFacebook(Context context, CallbackManager callbackManager, Uri uri) {
//        Toast.makeText(context, "start sharing to facebook, " + uri.getPath(), Toast.LENGTH_SHORT).show();
//        //com.facebook.orca
//        //com.facebook.katana
//        //com.example.facebook
//        //com.facebook.android
//        String facebookPackage = "com.facebook.katana";
//        if (AppUtil.newInstance().isAppInstalled(context, facebookPackage)) {
//            //using default intent
////            Intent intent = new Intent(Intent.ACTION_SEND);
////            intent.setType("image/*");
////            intent.putExtra(Intent.EXTRA_STREAM, uri);
////            intent.putExtra(Intent.EXTRA_TEXT, "this is a simple extra text");
////            intent.setPackage(facebookPackage);
//////            startActivity(Intent.createChooser(intent, "pick an app to ..."));
////            startActivity(intent);
//
//            //using sdk but native facebook app has been installed
//            sharePhotoToFacebook(context, callbackManager, uri);
//
//        } else {
//            loginAndShareToFacebook(context, callbackManager, uri);
//        }
//
//
//    }
//
//    private void sharePhotoToFacebook(final Context context, CallbackManager callbackManager, Uri uri) {
//        Bitmap bitmap;
//        try {
//            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
//            SharePhoto photo = new SharePhoto.Builder()
//                    .setBitmap(bitmap)
//                    .setCaption("Give me my codez or I will ... you know, do that thing you don't like!")
//                    .build();
//
//            SharePhotoContent sharePhotoContent = new SharePhotoContent.Builder()
//                    .addPhoto(photo)
//                    .build();
//
//            ShareDialog shareDialog = new ShareDialog((Activity) context);
//            shareDialog.setCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
//                @Override
//                public void onSuccess(Sharer.Result result) {
//                    Toast.makeText(context, "your image has been shared", Toast.LENGTH_SHORT).show();
//
//                }
//
//                @Override
//                public void onRightButtonClicked() {
//                    Toast.makeText(context, "sharing is canceled", Toast.LENGTH_SHORT).show();
//
//                }
//
//                @Override
//                public void onError(FacebookException error) {
//                    Toast.makeText(context, "failed to share your image", Toast.LENGTH_SHORT).show();
//                    error.printStackTrace();
//                }
//            });
//
//            if (ShareDialog.canShow(SharePhotoContent.class))
//                shareDialog.show(sharePhotoContent);
//            else
//                ShareApi.share(sharePhotoContent, new FacebookCallback<Sharer.Result>() {
//                    @Override
//                    public void onSuccess(Sharer.Result result) {
//                        Toast.makeText(context, "your image has been shared", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onRightButtonClicked() {
//                        Toast.makeText(context, "sharing is canceled", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(FacebookException error) {
//                        Toast.makeText(context, "failed to share your image", Toast.LENGTH_SHORT).show();
//                        error.printStackTrace();
//                    }
//                });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * @param uri which locate the image
//     * @see <a href="http://simpledeveloper.com/how-to-share-an-image-on-facebook-in-android/">references</a>
//     */
//    private void loginAndShareToFacebook(final Context context, final CallbackManager callbackManager, final Uri uri) {
//        List<String> permissionNeeds = Arrays.asList("publish_actions");
//
//        //this loginManager helps you eliminate adding a LoginButton to your UI
//        LoginManager mLoginManager = LoginManager.newInstance();
//        mLoginManager.logInWithPublishPermissions((Activity) context, permissionNeeds);
//        mLoginManager.setCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                sharePhotoToFacebook(context, callbackManager, uri);
//            }
//
//            @Override
//            public void onRightButtonClicked() {
//                Log.e(getClass().getName(), "login is canceled");
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Log.e(getClass().getName(), "login is error");
//            }
//        });
//    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////                                               ////////////////////////
    /////////////////////////            Twitter                            ////////////////////////
    /////////////////////////                                               ////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * share an image to twitter using fabric
     * there a 3 variants way to do this purpose
     * the first way uses default intent
     * the second way uses Twitter Tweet Composer
     * the third way uses TwitterKit Tweet Composer
     *
     * @see <a href="https://docs.fabric.io/android/twitter/compose-tweets.html"> read more about fobric</a>
     */
    private void shareToTwitter(Context context, Uri uri) {
        Toast.makeText(context, "start sharing to twitter " + uri.getPath(), Toast.LENGTH_SHORT).show();
        String twitterPackage = "com.twitter.android";
        if (AppUtil.getInstance().isAppInstalled(context, twitterPackage)) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.putExtra(Intent.EXTRA_TEXT, "the simple text");
            intent.setType("image/*");
            intent.setPackage(twitterPackage);
            context.startActivity(intent);

            //using Twitter Tweet Composer
            //The Twitter Android Application allows apps to start the exported Tweet composer via an Intent.
            //The Twitter Android compose is feature-rich, familiar to users, and has options for attaching images and videos.
            //If the Twitter app is not installed, the intent will launch twitter.com in a browser, but the specified image will be ignored.
//            TweetComposer.Builder builder = new TweetComposer.Builder(this)
//                    .text("just setting up my Fabric.")
//                    .image(uri);
//            builder.show();

        } else {
            //The TwitterKit Tweet Composer (Beta) is a lightweight composer which lets users compose Tweets with App Cards from within your application.
            // It does not depend on the Twitter for Android app being installed.

//            Card card = new Card.AppCardBuilder(LanguageChoiceActivity.this)
//                    .imageUri(uri)
//                    .iPhoneId("123456")
//                    .iPadId("654321")
//                    .build();
//
//            TwitterSession session = TwitterCore.newInstance().getSessionManager().getActiveSession();
//            Intent intent = new ComposerActivity.Builder(LanguageChoiceActivity.this).session(session).card(card).createIntent();
//            startActivity(intent);
            Toast.makeText(context, "to use this feature, you must have an Twitter application is installed", Toast.LENGTH_SHORT).show();
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////                                               ////////////////////////
    /////////////////////////            Instagram                          ////////////////////////
    /////////////////////////                                               ////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * share an image to instagram application
     * note: instagram don't allow sharing text
     *
     * @param path which locate the image
     * @see <a href="https://instagram.com/developer/mobile-sharing/android-intents/">Instagram docs</a>
     */
    private void shareToInstagram(Context context, String path) {
        Toast.makeText(context, "start sharing to instagram " + path, Toast.LENGTH_SHORT).show();
        if (path == null || path.isEmpty()) {
            Toast.makeText(context, "path is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        shareToInstagram(context, Uri.parse("file://" + path));
    }

    /**
     * share an image to instagram application
     * note: instagram don't allow sharing text
     *
     * @param uri which locate the image
     * @see <a href="https://instagram.com/developer/mobile-sharing/android-intents/">Instagram docs</a>
     */

    private static void shareToInstagram(Context context, Uri uri) {//content://media/external/images/media/451
        // /storage/emulated/0/メリーズ スマイルDays/20151106_032146.jpg
        // /storage/emulated/0/メリーズ スマイルDays/20151106_020312.jpg

        Toast.makeText(context, "start sharing to instagram " + uri.getPath(), Toast.LENGTH_SHORT).show();
        String instagramPackage = "com.instagram.android";
        if (AppUtil.getInstance().isAppInstalled(context, instagramPackage)) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.setPackage(instagramPackage);
//            startActivity(Intent.createChooser(intent, "Share to"));
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "to use this feature, you must have an Instagram application is installed", Toast.LENGTH_SHORT).show();
        }

    }
}
