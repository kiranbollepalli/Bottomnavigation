package mobi.garden.bottomnavigationtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public abstract class BaseActivity extends AppCompatActivity
    implements BottomNavigationView.OnNavigationItemSelectedListener {

  protected BottomNavigationView navigationView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getContentViewId());

    navigationView = (BottomNavigationView) findViewById(R.id.navigation);
    navigationView.setOnNavigationItemSelectedListener(this);
    initializeUI();
  }

  @Override protected void onStart() {
    super.onStart();
    updateNavigationBarState();
  }

  // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
  @Override public void onPause() {
    super.onPause();
    overridePendingTransition(0, 0);
  }

  @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    navigationView.postDelayed(() -> {
      int itemId = item.getItemId();

      switch (itemId) {
        case R.id.navigation_home:
          startActivity(new Intent(this, HomeActivity.class));

          break;
        case R.id.navigation_dashboard:
          startActivity(new Intent(this, DashboardActivity.class));

          break;
        case R.id.navigation_notifications:
          startActivity(new Intent(this, NotificationsActivity.class));

          break;
        case R.id.navigation_tabs:
          startActivity(new Intent(this, TabActivity.class));

          break;
      }

      finish();
    }, 300);
    return true;
  }

  private void updateNavigationBarState() {
    int actionId = getNavigationMenuItemId();
    selectBottomNavigationBarItem(actionId);
  }

  void selectBottomNavigationBarItem(int itemId) {
    Menu menu = navigationView.getMenu();
    for (int i = 0, size = menu.size(); i < size; i++) {
      MenuItem item = menu.getItem(i);
      boolean shouldBeChecked = item.getItemId() == itemId;
      if (shouldBeChecked) {
        item.setChecked(true);
        break;
      }
    }
  }

  abstract int getContentViewId();

  abstract int getNavigationMenuItemId();

  abstract void initializeUI();
}
