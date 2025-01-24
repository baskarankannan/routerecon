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
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class CreateAccountEspressoTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);


    @Before
    public void setUp() throws Exception{
        //mActivityRule.getActivity().getSupportFragmentManager().beginTransaction().add(new LoginFragment(),null).commit();
        onView(withId(R.id.tv_create_new)).perform(click());


    }

    @Test
    public void successfulCreateAccountCheck() throws Exception{

        onView(withId(R.id.tv_create_new)).perform(click());



        onView(withId(R.id.et_full_name))
                .check(matches(withHint(R.string.full_name)))
                .perform(typeText("Test 1"))
                .check(matches(isDisplayed()));

        onView(withId(R.id.et_email))
                .check(matches(withHint(R.string.email)))
                .perform(typeText("wwe@dev.com"))
                .check(matches(isDisplayed()));

        onView(withId(R.id.et_user_name))
                .check(matches(withHint(R.string.user_name)))
                .perform(typeText("Test 1"))
                .check(matches(isDisplayed()));

        onView(withId(R.id.et_phone_number))
                .check(matches(withHint(R.string.text_phone_required)))
                .perform(typeText("01734757473"))
                .check(matches(isDisplayed()));

        onView(withId(R.id.et_password))
                .check(matches(withHint(R.string.password)))
                .perform(typeText("123456"))
                .check(matches(isDisplayed()));

        onView(withId(R.id.et_confirm_password))
                .check(matches(withHint(R.string.confirm_pass)))
                .perform(typeText("123456"))
                .check(matches(isDisplayed()));

        onView(withId(R.id.btn_create_account)).perform(click());

        assertNotNull(onView(withId(R.id.et_full_name)));
        assertNotNull(onView(withId(R.id.et_email)));
        assertNotNull(onView(withId(R.id.et_user_name)));
        assertNotNull(onView(withId(R.id.et_phone_number)));
        assertNotNull(onView(withId(R.id.et_password)));
        assertNotNull(onView(withId(R.id.et_confirm_password)));
    }

    @After
    public void tearDown() throws Exception{

    }
}
