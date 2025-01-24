package com.techacsent.route_recon;

import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import androidx.test.filters.MediumTest;

import com.techacsent.route_recon.activity.LoginActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class LoginEspressoTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);


    @Before
    public void setUp() throws Exception{
        //mActivityRule.getActivity().getSupportFragmentManager().beginTransaction().add(new LoginFragment(),null).commit();

    }

    @Test
    public void testSuccessfullLogin() throws Exception{


        onView(withId(R.id.et_email))
                .check(matches(withHint(R.string.email)))
                .perform(typeText("alex@dao.com"))
                .check(matches(isDisplayed()));

        onView(withId(R.id.et_password))
                .check(matches(withHint(R.string.password)))
                .perform(typeText("12345678"))
                .check(matches(isDisplayed()));

        onView(withId(R.id.btn_login)).perform(click());

        assertNotNull(onView(withId(R.id.et_email)));

        assertNotNull(onView(withId(R.id.et_password)));

    }


    @Test
    public void testFailedLogin() throws Exception{

        onView(withId(R.id.btn_login)).perform(click());

        //onView(withText(R.string.email_not_valid)).check(matches(isDisplayed()));

        onView(withId(R.id.et_email))
                .check(matches(withHint(R.string.email)))
                .perform(typeText("alexs@dao.com"))
                .check(matches(isDisplayed()));

        onView(withId(R.id.et_password))
                .check(matches(withHint(R.string.password)))
                .perform(typeText("12345678"))
                .check(matches(isDisplayed()));

        onView(withId(R.id.btn_login)).perform(click());

        assertNotNull(onView(withId(R.id.et_email)));

    }

    @After
    public void tearDown() throws Exception{

    }
}
