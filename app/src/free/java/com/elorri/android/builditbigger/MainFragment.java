package com.elorri.android.builditbigger;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {

    private AdView mAdBannerView = null;
    private InterstitialAd mInterstitialAd = null;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        // Load interstitial Ad.
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                displayJokeActivity();
            }
        });
        requestNewInterstitial();

        Button tellJokeButton = (Button) root.findViewById(R.id.tellJoke);
        tellJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    displayJokeActivity();
                }
            }
        });

        mAdBannerView = (AdView) root.findViewById(R.id.adView);
        requestBannerAd();
        return root;
    }

    private void requestBannerAd() {
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdBannerView.loadAd(adRequest);
    }


    private void displayJokeActivity() {
        new GCEndpointsApiService(getActivity()).execute(getContext());
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(adRequest);
    }
}


