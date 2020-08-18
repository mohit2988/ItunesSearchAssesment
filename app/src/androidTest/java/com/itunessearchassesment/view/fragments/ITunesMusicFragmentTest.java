package com.itunessearchassesment.view.fragments;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.itunessearchassesment.MainActivity;
import com.itunessearchassesment.R;
import com.itunessearchassesment.middelware.model.ITunesResultResponse;
import com.itunessearchassesment.middelware.viewmodel.ITunesMusicViewModel;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class ITunesMusicFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
    }


    @Test
    public void callAlbumService_showData_ScrollList() {
        // GIVEN
        FragmentScenario<ITunesMusicFragment> scenario = FragmentScenario.launchInContainer(
                ITunesMusicFragment.class,
                null,
                R.style.AppTheme,
                null);
        AtomicReference<ITunesMusicFragment> iTunesMusicFragment = new AtomicReference<>();
        NavController navController = mock(NavController.class);
        scenario.onFragment(fragment -> {
                    iTunesMusicFragment.set(fragment);
                    Navigation.setViewNavController(fragment.requireView(), navController);
                }
        );

        try {
            mActivityRule.runOnUiThread(() -> iTunesMusicFragment.get().setToolBarData(mActivityRule.getActivity()));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        pauseTestFor(1000);

        ITunesMusicViewModel iTunesMusicViewModel = new ViewModelProvider(iTunesMusicFragment.get()).get(ITunesMusicViewModel.class);

        Observer<List<ITunesResultResponse.Result>> tokenObserver = mock(Observer.class);

        try {
            // Observe the LiveData forever
            iTunesMusicViewModel.getResultsMutableLiveData().observeForever(tokenObserver);

            // WHEN
            iTunesMusicViewModel.getAllAlbums();
            pauseTestFor(5000);

            List<ITunesResultResponse.Result> results = iTunesMusicViewModel.getResultsMutableLiveData().getValue();

            Assert.assertNotEquals(results, null);
            Assert.assertNotEquals(results.size(), 0);
            Assert.assertTrue(results.size() > 0);

            // THEN - Verify that we navigate to the add screen
//            verify(navController).navigateUp();
        } finally {
            // Whatever happens, don't forget to remove the observer!
            iTunesMusicViewModel.getResultsMutableLiveData().removeObserver(tokenObserver);
        }

    }


    private void pauseTestFor(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
    }
}