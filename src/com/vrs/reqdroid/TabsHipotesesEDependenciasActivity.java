/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENSE.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;


/**
 * Implementa a activity que contem as abas (tabs) das telas de hipoteses e dependencias do aplicativo.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public class TabsHipotesesEDependenciasActivity extends SherlockFragmentActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar bar = getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setDisplayShowTitleEnabled(true);
        bar.addTab(bar
                .newTab()
                .setText(getResources().getString(R.string.tela_tabs_hed_hipoteses))
                .setTabListener(
                        new TabListener<HipotesesFragment>(this, "tab1",
                                HipotesesFragment.class, null)));

        bar.addTab(bar
                .newTab()
                .setText(getResources().getString(R.string.tela_tabs_hed_dependencias))
                .setTabListener(
                        new TabListener<DependenciasFragment>(this, "tab2",
                                DependenciasFragment.class, null)));

        if (savedInstanceState != null) {
            bar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
        }
    }

        public class TabListener<T extends SherlockFragment> implements ActionBar.TabListener {
            private final SherlockFragmentActivity mActivity;
            private final String mTag;
            private final Class<T> mClass;
            private final Bundle mArgs;
            private SherlockFragment mFragment;

            public TabListener(SherlockFragmentActivity activity, String tag, Class<T> clz,
                               Bundle args) {
                mActivity = activity;
                mTag = tag;
                mClass = clz;
                mArgs = args;
                FragmentTransaction ft = mActivity.getSupportFragmentManager()
                        .beginTransaction();

                // Check to see if we already have a fragment for this tab, probably
                // from a previously saved state. If so, deactivate it, because our
                // initial state is that a tab isn't shown.
                mFragment = (SherlockFragment) mActivity.getSupportFragmentManager().findFragmentByTag(mTag);
                if (mFragment != null && !mFragment.isDetached()) {
                    ft.detach(mFragment);
                }
            }
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                ft = mActivity.getSupportFragmentManager()
                        .beginTransaction();

                if (mFragment == null) {
                    mFragment = (SherlockFragment) SherlockFragment.instantiate(mActivity, mClass.getName(), mArgs);
                    ft.add(android.R.id.content, mFragment, mTag);
                    ft.commit();
                } else {
                    ft.attach(mFragment);
                    ft.commit();
                }
            }
            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                ft = mActivity.getSupportFragmentManager().beginTransaction();
                if (mFragment != null) {
                    ft.detach(mFragment);
                    ft.commitAllowingStateLoss();
                }
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.menusobre, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpTo(this, new Intent(this, TelaVisaoGeralActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
