package me.poernomo.android.criminalintent;

import java.util.ArrayList;
import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

public class CrimePagerActivity extends FragmentActivity {

	private ViewPager mViewPager;
	private ArrayList<Crime> mCrimes;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);

		mCrimes = CrimeLab.get(this).getCrimes();

		FragmentManager fm = getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {

			@Override
			public Fragment getItem(int pos)
			{
				Crime crime = mCrimes.get(pos);
				return CrimeFragment.newInstance(crime.getId());
			}

			@Override
			public int getCount()
			{
				return mCrimes.size();
			}

		});

		// Which Crime should we display when we first load up?
		UUID crimeId = (UUID) getIntent().getSerializableExtra(
				CrimeFragment.EXTRA_CRIME_ID);
		Crime tempCrime;
		for (int i = 0; i < mCrimes.size(); i++)
		{
			tempCrime = mCrimes.get(i);
			if (tempCrime.getId().equals(crimeId))
			{
				mViewPager.setCurrentItem(i);
				setTitle(tempCrime.getTitle()); // fixed minor bug: not setting
												// activity title for first load
				break;
			}
		}

		mViewPager
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

					@Override
					public void onPageSelected(int arg0)
					{
						Crime crime = mCrimes.get(arg0);
						if (crime.getTitle() != null)
						{
							setTitle(crime.getTitle());
						}
					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2)
					{
						// intentionally blank

					}

					@Override
					public void onPageScrollStateChanged(int arg0)
					{
						// intentionally blank

					}
				});

	}

}
