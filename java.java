//数据 与 显示 分开

//工厂模式：
//将大量有共同接口的类实例化。
//比如会飞的动物这种；

// Simple Factory == Static Factory Method
// Factory Method == Polymorphic Factory == Virtual Constructor
// Abstract Factory == Kit == ToolKit

//静态工厂方法
public static Fruit factory(String which) throws BadFruitException{
	if(which.equalsIgnoreCase("apple")){
		return new Apple();
	}else if(which.equalsIgnoreCase("strawberry")){
		return new Strawberry();
	}else if(which.equalsIgnoreCase("grape")){
		return new Grape();
	}else{
		throw new BadFruitException("Bad fruit request");
	}
}

public class BadFruitException extends Exception{
	public BadFruitException(String msg){
		super(msg);
	}
}

try{
	FruitGardener.factory("grape");
	FruitGardener.factory("apple");
	FruitGardener.factory("strawberry");
}catch(BadFruitException e){
	//...
}

//简单工厂模式是类的创建模式
// 一个工厂类根据传入的参数决定创建出哪一类产品类的实例


public class Creator{
	public static Product factory(){
		return new ConcreteProduct();
	}
}

public interface Product{

}

public class ConcreteProduct implements Product{
	public ConcreteProduct(){

	}
}

//三个原本独立的角色 工厂角色、抽象产品以及具体产品角色 合并到一个类中

public class ConcreteProduct{
	public ConcreteProduct(){

	}

	public static ConcreteProduct factory(){
		return new ConcreteProduct();
	}
}

//工厂方法模式
public interface Creator{
	public Product factory();
}

public interface Product{

}

public class ConcreteCreator1 implements Creator{
	public Product factory(){
		return new ConcreteProduct1();
	}
}

public class ConcreteCreator2 implements Creator{
	public Product factory(){
		return new ConcreteProduct2();
	}
}

public class ConcreteProduct1 implements Product{
	public ConcreteProduct1(){
		// do something
	}
}

public class ConcreteProduct2 implements Product{
	public ConcreteProduct2(){
		// do something
	}
}

//演示
public class Client{
	private static Creator creator1,creator2;
	private static Product prod1,prod2;

	public static void main(String[] args){
		creator1 = new ConcreteCreator1();
		prod1 = creator1.factory();

		creator2 = new ConcreteCreator2();
		prod2 = creator2.factory();
	}
}

// 工厂方法模式之所以有一个别名叫 多态性工厂模式
// 具体工厂类都有共同的接口，或者都有共同的抽象父类


//View相关
public class SimpleLayout extends ViewGroup{

	public SimpleLayout(Context context, AttributeSet attrs){
		super(context,attrs);
	}

	protected void onMeasure(int widthMeasureSper, int heightMeasureSpec){
		super.onMeaser(widthMeasureSper,heightMeasureSpec);

		//如果有子视图，就调用measureChild()来测量出子视图的大小
		if(getChildCount() > 0){
			View childView = getChildAt(0);
			measureChild(childView,widthMeasureSper,heightMeasureSpec);
		}
	}

	protected void onLayout(boolean changed,int l, int t, int r, int b){
		if(getChildCount() > 0){
			View childView = getChildAt(0);
			childView.layout(0,0,childView.getMeasureWidth(),childView.getMeasureHeight());
		}
	}
}

// View你们的canvas
public class MyView extends View implements OnClickListener{
	private Paint mPaint;

	public MyView(Context context, AttributeSet attrs){
		super(context,attrs);
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	}

	public void onDraw(Canvas canvs){
		mPaint.setColor(Color.YELLOW);
		canvas.drawRect(0,0,getWidth(),getHeight(),mPaint);

		mPaint.setColor(Color.Blue);
		mPaint.setTextSize(20);
		String text = "Hello View";
		canvas.drawText(text,0,getHeight()/2, mPaint);
	}

	@Override  
    public void onClick(View v) {  
         
        invalidate();  
    } 
}

//首先我们在CounterView的构造函数中初始化了一些数据，
//并给这个View的本身注册了点击事件，这样当CounterView被点击的时候，onClick()方法就会得到调用。

//组合控件
public class TitleView extends FrameLayout{

	private Button leftButton;
	private TextView titleText;

	public TitleView(Context context, AttributeSet attrs){
		super(context, attrs);

		LayoutInflater.from(context).inflate(R.layout.title, this);

		titleText = (TextView)findViewById(R.id.title_text);
		leftButton = (Button) findViewById(R.id.button_left);

		leftButton.setOnClickListenser(new OnClickListener(){
			public void onClick(View v){
				((Activity)getContext()).finish();
			}
		});
	}

	public void setTitleText(String text){
		titleText.setText(text);
	}

	public void setLeftButtonText(String text){
		leftButton.setText(text);
	}

	public void setLeftButtonListener(OnClickListener l){
		leftButton.setOnClickListenser(l);
	}
}

//继承控件
public class MyListView extends ListView implements OnTouchListener,OnGestureLisener{
	
	private GestureDetetor gestureDector;
	private onDeleteListener listener;
	private View deleteButton;
	private ViewGroup itemLayout;
	private int selectedItem;
	private boolean isDeleteShown;

	public MyListView(Context context,AttributeSet attrs){
		super(context, attrs);

		gestureDector = new GestureDetetor(getContext(),this);
		setOnTouchListener(this);
	}

	public void setOnDeleteListener(onDeleteListener l){
		listener = l;
	}

	public boolean onTouch(View v, MotionEvent event){
		if(isDeleteShown){
			itemLayout.removeView(deleteButton);
			deleteButton = null;
			isDeleteShown = false;
			return false;
		}else{
			return gestureDector.onTouchEvent(event);
		}
	}

	public boolean onDown(MotionEvent e){
		if(!isDeleteShown){
			selectedItem = pointToPosition((int)e.getX(),(int)e.getY());
		}
		return false;
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
		if(!isDeleteShown && Math.abs(velocityX) > Math.abs(velocityY)){
			deleteButton = LayoutInflater.from(getContext().inflate(R.layout.delete_button,null));
			deleteButton.setOnClickListener(new OnClickListener(){
				public void onClick(View v){
					itemLayout.removeView(deleteButton);
					deleteButton = null;
					isDeleteShown = false;
					listener.onDelete(selectedItem);
				}
			});

			itemLayout = (ViewGroup)getChildAt(selectedItem - getFirstVisibilePosition());

			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_PARAENT_RIGHT);
			params.addRule(RelativeLayout.CENTER_VERTICAL);

			itemLayout.addView(deleteButton,params);
			isDeleteShown = true;
		}

		return false;
	}

	public boolean onSingleTapUp(MotionEvent e){
		return false;
	}

	public void onShowPress(MotionEvent e){

	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2,float distanceX, float distanceY){
		return false;
	}

	public void onLongPress(MotionEvent e){

	}

	public interface OnDeleteListener{
		void onDelete(int index);
	}
}


// Service 的基本用法
public class MyService extends Service{

	public static final mBinder = new MyBinder();

	public void onCreate(){
		super.onCreate();
		//...
	}

	public int onStartCommand(Intent intent, int flags, int startId){
		return super.onStartCommand(intent,flags,startId);
	}

	public void onDestory(){
		super.onDestory();
	}

	public IBinder onBind(Intent intent){
		return mBinder;
	}

	class MyBinder extends Binder{
		public void startDownload(){
			//...
		}
	}
}

public class MainActivity extends Activity implements OnClickListener{
	private MyService.MyBinder myBinder;

	private ServiceConnection connection = new ServiceConnection{

		public void onServiecDisconnected(ComponentName name){

		}

		public void onServiceConnected(ComponentName name,IBinder service){
			myBinder = (MyService.MyBinder) service;
			myBinder.startDownload();
		}
	}

	public void onClick(View v){

		Intent bindIntent = new Intent(this, MyService.class);
		bindService(bindIntent,connection,BIND_AUTO_CREATE);

		unbindService(connection);
	}
}

//通过一个MyBinder（Binder） ，建立activity和service之间的联系
//建立连接时，需要intent 和 connection（ServiceConnection） 及flags
//断开连接时，需要connection

//这里传入BIND_AUTO_CREATE表示在Activity和Service建立关联后自动创建Service，
//这会使得MyService中的onCreate()方法得到执行，但onStartCommand()方法不会执行。


//前台service 提高优先级 避免被杀掉

public class MyService extends Service{

	public static final String TAG = "MyService";

	private MyBinder mBinder = new MyBinder();

	public void onCreate(){
		super.onCreate();

		Notification notification = new Notification(R.drawable.ic_launcher,"xxx
			",System.currentTimeMillis());
		Intent notificationIntent =  new Intent(this, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);
		notification.setLatestEventInfo(this,"title","content",pendingIntent);
		startForeground(1,notifacation);
	}
}

//远程Service 实现跨进程通信IPC
<service  
        android:name="com.example.servicetest.MyService"  
        android:process=":remote" >  
</service>
// remote service 不设置的话（AIDL），不能与activity进行通信
// AIDL Android Interface Definition Language 

// 首先创建 AIDL文件
// 定义a与s 进行通信的方法
// 新建 MyAIDLService.aidl
// Stub 就是 Binder的子类

interface MyAIDLService{
	int plus(int a, int b);
	String toUpperCase(String str);
}

public class MyService extends Service{

	public IBinder onBind(Intent intent){
		return mBinder;
	}

	MyAIDLService.Stub mBinder = new Stub(){
		public String toUpperCase(String str) throws RemoteException{
			if(str != null){
				return str.toUpperCase();
			}
			return null;
		}

		public int plus(int a, int b) throws RemoteException{
			return a + b;
		}
	}
}

public class MainActivity extends Activity implements OnClickListener{

	private MyAIDLService myAIDLService;

	private ServiceConnection connection = new ServiceConnection(){

		public void onServiceDisconnected(ComponentName name){

		}

		public void onServiceConnected(ComponentName name, IBinder service){
			myAIDLService = MyAIDLService.Stub.asInterface(service);
			try{
				int result = myAIDLService.plus(3,5);
				String upperStr = myAIDLService.toUpperCase("xxx");
			}catch(){
				//...
			}
		}
	}
}

// 在actionbar的使用中，父子之间的跳转

public boolean onOptionsItemSelected(MenuItem item){

	switch(item.getItemId()){
		case android.R.id.home:
		Intent upIntent = NavUtils.getParentActivityIntent(this);

		// 如果父子在同一个Task中，则直接调用 upto 进行跳转，
		// 如果不在同一个Task中，则需要借助builder 来创建一个新的Task


		if(NavUtils.shouldUpRecreateTask(this,upIntent)){
			TaskStackBuilder.create(this)
			.addNextIntentWithParentStack(upIntent)
			.startActivities();
		}else{
			upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			NavUtils.navigateUpTo(this,upIntent);
		}
		return true;
	}
}

// 使用fragment 制作类似tabhost效果

public class MainActivity extends Activity implements OnClickListener{
	private Fragment fg1;
	private Fragment fg2;
	private Fragment fg3;
	private Fragment fg4;


	private void setTabSelection(int index){
		clearSelection();

		FragmentTransaction transaction = fragmentManager.beginTransaction();

		hideFragment(transaction);

		siwtch(index){
			case 0:
				if(messageFragment == null){
					messageFragment = new messageFragment();
					transaction.add(R.id.content,messageFragment);
				}else{
					transaction.show(messageFragment);
				}
				break;
			case 1:
				// ...
				break;
		}
	}

	private void hideFragment(FragmentTransaction transaction){
		if(messageFragment != null){
			transaction.hide(messageFragment);
		}

		// ...
	}
}

// 平板双栏fragment的注意


public void onActivityCreated(Bundle savedInstanceState){
	super.onActivityCreated(savedInstanceState);

	if(getActivity().findViewById(R.id.other) != null){
		isTwoPane = true;
	}else{
		isTwoPane = false;
	}
}

// Palette 图片着色分析器
// 已经是后台线程 用 Palette.generate();
// 主线程的话 用 Palette.generateAsync();
// 提供一个监听器去替代
Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener{

	public void onGenerated(Palette palette){
		Palette.Swatch vibrant = palette.getVribrantSwatch();
		if(swatch != null){
			titleView.setBackgroundColor(vibrant.getRgb());
			titleView.setTextColor(vibrant.getTitleTextColor());
		}
	}
});

if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
    getWindow().setStatusBarColor(Color.BLUE);
}

// svg 矢量图形 AnimatedVectorDrawable
//http://blog.csdn.net/cym492224103/article/details/41677825

// RecyclerView
// 为每个条目位置提供了布局管理器
// 为每个条目设置了操作动画


protected void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	final RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
	final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
	recyclerView.setLayoutManager(layoutManager);

	final SampleRecyclerAdapter sampleRecyclerAdapter = new SampleRecyclerAdapter();
	recyclerView.setAdapter(sampleRecyclerAdapter);

}

public class SampleRecyclerAdapter extends 
	RecyclerView.Adapter<SampleRecyclerAdapter.ViewHolder>{

	private final  ArrayList<SampleModel> sampleData = DemoApp.getSampleData(20);

	public ViewHolder onCreateViewHolder(ViewGroup parentViewGroup, int i){
		View item = LayoutInflater.from(parentViewGroup.getContext()).inflate(
				R.layout.list_basic_item,parentViewGroup,false);

		return new ViewHolder(item);
	}

	public void onBindViewHolder(ViewHolder viewHolder,final int position){
		final SampleModel rowData = sampleData.get(position);

		viewHolder.textViewSample.setText(rowData.getSampleText());
		viewHolder.itemView.setTag(rowData);
	}

	public int getItemCount(){
		return sampleData.size();
	}

	public void removeData(int position){
		sampleData.remove(position);
		notifyItemRemove(position);
	}

	public void addItem(int positionToAdd){
		sampleData.add(positionToAdd,new SampleModel());
		notifyItemInserted(positionToAdd);
	}

	public static class ViewHolder extends RecyclerView.ViewHolder{
		private final TextView textViewSample;

		public ViewHolder(View itemView){
			super(itemView);

			textViewSample = (TextView)itemView.findViewById(R.id.textViewSample);
		}
	}

}

// 官方的例子
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        String [] myDataset = {"Android","ios","jack","tony","window","mac","1234","hehe","495948"};

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // improve performance if you know that changes in content
        // do not change the size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        //设置RecycleView的显示方向：（默认为垂直） 水平
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }
// 有VIewHolder类 类里是控件
// 通过onCreateViewHolder方法将布局id传到ViewHolder中
// 在onBindViewHolder 中绑定数据

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private String[] mDataset;

        // Provide a reference to the type of views that you are using
        // (custom viewholder)
        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;

            public ViewHolder(TextView v) {
                super(v);
                mTextView = v;
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(String[] myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.my_text_view, parent, false);
            // set the view's size, margins, paddings and layout parameters

            ViewHolder vh = new ViewHolder((TextView) v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.mTextView.setText(mDataset[position]);

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.length;
        }
    }

// 它的作用就是共享两个acitivity种共同的元素，在Android 5.0下支持如下效果：
// changeBounds -  改变目标视图的布局边界
// changeClipBounds - 裁剪目标视图边界
// changeTransform - 改变目标视图的缩放比例和旋转角度
// changeImageTransform - 改变目标图片的大小和缩放比例

// View在UI线程去更新自己；而SurfaceView则在一个子线程中去更新自己
// SurfaceView 的 getHolder方法，我们可以获取一个SurfaceViewHolder。
// 通过holder可以监听view的生命周期以及获取canvas对象

public class SurfaceViewTemplate extends SurfaceView implements Callback,Runnable{
	
	private SurfaceHolder mHolder;
	private Canvas mCanvas;
	private Thread t;
	private boolean isRunning;

	public SurfaceViewTemplate(Context context){
		this(context,null);
	}

	public SurfaceViewTemplate(Context context, AttributeSet attrs){
		super(context,attrs);

		mHolder = getHolder();
		mHolder.addCallback(this);

		setFocusable(true);
		setFocusableInTouchMode(true);

		this.setKeepScreenOn(true);
	}

	public void surfaceCreated(SurfaceHolder holder){
		isRunning = true;
		t = new Thread(this);
		t.start();
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height){
		//...
	}

	public void surfaceDestroyed(SurfaceHolder holder){
		isRunning = false;
	}

	public void run(){
		while(isRunning){
			draw();
		}
	}

	private void draw(){
		try{
			mCanvas = mHolder.lockCanvas();
			if(mCanvas != null){
				// ...
			}
		}catch(){

		}finally{
			if(mCanvas != null){
				mHolder.unlockCanvasAndPost(mCanvas);
			}
		}
	}
}

protected void onMeasure(int widthMeasureSper , int heightMeasureSpec){
	super.onMeasure(widthMeasureSpec,heightMeasureSpec);

	int width = Math.min(getMeasureWidth(),getMeasureHeight());

	// ...

	setMeasureDimension(width, width);
}

// touch event


//在actionbar上面动态添加按钮
public boolean onCreateOptionMenu(Menu menu){
	getMenuInflater().inflate(R.menu.main,menu);

	MenuItem locationItem = menu.add(0,R.id.menu_location,0,"Location");
	locationItem.setIcon(R.drawable.ic_action_location);

	MenuItemCompat.setShowAsAction(locationItem,MenuItem.SHOW_AS_ACTION_IF_ROOM);
}


// viewpager带上面标题的
public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
     * will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    /**
     * Create the activity. Sets up an {@link android.app.ActionBar} with tabs, and then configures the
     * {@link ViewPager} contained inside R.layout.activity_main.
     *
     * <p>A {@link SectionsPagerAdapter} will be instantiated to hold the different pages of
     * fragments that are to be displayed. A
     * {@link android.support.v4.view.ViewPager.SimpleOnPageChangeListener} will also be configured
     * to receive callbacks when the user swipes between pages in the ViewPager.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the UI from res/layout/activity_main.xml
        setContentView(R.layout.sample_main);

        // Set up the action bar. The navigation mode is set to NAVIGATION_MODE_TABS, which will
        // cause the ActionBar to render a set of tabs. Note that these tabs are *not* rendered
        // by the ViewPager; additional logic is lower in this file to synchronize the ViewPager
        // state with the tab state. (See mViewPager.setOnPageChangeListener() and onTabSelected().)
        // BEGIN_INCLUDE (set_navigation_mode)
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // END_INCLUDE (set_navigation_mode)

        // BEGIN_INCLUDE (setup_view_pager)
        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        // END_INCLUDE (setup_view_pager)

        // When swiping between different sections, select the corresponding tab. We can also use
        // ActionBar.Tab#select() to do this if we have a reference to the Tab.
        // BEGIN_INCLUDE (page_change_listener)
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });
        // END_INCLUDE (page_change_listener)

        // BEGIN_INCLUDE (add_tabs)
        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter. Also
            // specify this Activity object, which implements the TabListener interface, as the
            // callback (listener) for when this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
        // END_INCLUDE (add_tabs)
    }

    /**
     * Update {@link ViewPager} after a tab has been selected in the ActionBar.
     *
     * @param tab Tab that was selected.
     * @param fragmentTransaction A {@link android.app.FragmentTransaction} for queuing fragment operations to
     *                            execute once this method returns. This FragmentTransaction does
     *                            not support being added to the back stack.
     */
    // BEGIN_INCLUDE (on_tab_selected)
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, tell the ViewPager to switch to the corresponding page.
        mViewPager.setCurrentItem(tab.getPosition());
    }
    // END_INCLUDE (on_tab_selected)

    /**
     * Unused. Required for {@link android.app.ActionBar.TabListener}.
     */
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * Unused. Required for {@link android.app.ActionBar.TabListener}.
     */
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    // BEGIN_INCLUDE (fragment_pager_adapter)
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages. This provides the data for the {@link ViewPager}.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
    // END_INCLUDE (fragment_pager_adapter)

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // BEGIN_INCLUDE (fragment_pager_adapter_getitem)
        /**
         * Get fragment corresponding to a specific position. This will be used to populate the
         * contents of the {@link ViewPager}.
         *
         * @param position Position to fetch fragment for.
         * @return Fragment for specified position.
         */
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a DummySectionFragment (defined as a static inner class
            // below) with the page number as its lone argument.
            Fragment fragment = new DummySectionFragment();
            Bundle args = new Bundle();
            args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
            fragment.setArguments(args);
            return fragment;
        }
        // END_INCLUDE (fragment_pager_adapter_getitem)

        // BEGIN_INCLUDE (fragment_pager_adapter_getcount)
        /**
         * Get number of pages the {@link ViewPager} should render.
         *
         * @return Number of fragments to be rendered as pages.
         */
        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
        // END_INCLUDE (fragment_pager_adapter_getcount)

        // BEGIN_INCLUDE (fragment_pager_adapter_getpagetitle)
        /**
         * Get title for each of the pages. This will be displayed on each of the tabs.
         *
         * @param position Page to fetch title for.
         * @return Title for specified page.
         */
        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
        // END_INCLUDE (fragment_pager_adapter_getpagetitle)
    }

    /**
     * A dummy fragment representing a section of the app, but that simply displays dummy text.
     * This would be replaced with your application's content.
     */
    public static class DummySectionFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        public static final String ARG_SECTION_NUMBER = "section_number";

        public DummySectionFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_dummy, container, false);
            TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
            dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

}

// 屏幕截图
// 现在，Android 5.0应用可以自行捕捉屏幕，
//首先用参数MEDIA_-PROJECTION_SERVICE调用Context.getSystemService()，
//得到MediaProjectionManager类别实例；
//其次，调用createScreenCaptureIntent ()得到一个Intent；再次，使用startActivityForResult()启动屏幕捕捉；
//最后，将结果返回到getMediaProjection()上，获取捕捉数据。

//RecyclerView 继续
// RecyclerView.Adapter
// RecyclerView.LayoutManager 数据显示布局方式
// RecyclerView.ViewHolder

public static abstract class Adapter<VH extends ViewHolder>{}{

	public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

	public abstract void onBindViewHolder(VH holder,int position);

	public abstract int getItemCount();
}

// LinearLayoutManager
// GridLayoutManager
// StaggerdGridLayoutManager

// 屏幕
DisplayMetrics dm = new DisplayMetrics();
dm = getResources().getDisplayMetrics();

float density = dm.density;        // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
int densityDPI = dm.densityDpi;
Toast.makeText(this, "radio->" + density + ",dpi->" + densityDPI, Toast.LENGTH_LONG).show();

//波纹
//android:background="?android:attr/selectableItemBackground"波纹有边界
//android:background="?android:attr/selectableItemBackgroundBorderless"波纹超出边界

// L通知
@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNotificationManager = (NotificationManager) getActivity().getSystemService(Context
                .NOTIFICATION_SERVICE);
    }
    
Notification createNotification(boolean makeHeadsUpNotification) {
        Notification.Builder notificationBuilder = new Notification.Builder(getActivity())
                .setSmallIcon(R.drawable.ic_launcher_notification)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setContentTitle("Sample Notification")
                .setContentText("This is a normal notification.");
        if (makeHeadsUpNotification) {
            Intent push = new Intent();
            push.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            push.setClass(getActivity(), LNotificationActivity.class);

            PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(getActivity(), 0,
                    push, PendingIntent.FLAG_CANCEL_CURRENT);
            notificationBuilder
                    .setContentText("Heads-Up Notification on Android L or above.")
                    .setFullScreenIntent(fullScreenPendingIntent, true);
        }
        return notificationBuilder.build();
    }
//statusbar 系统状态栏  
Window window = activity.getWindow();
window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
window.setStatusBarColor(activity.getResources().getColor(R.color.example_color));

////////////////////////////////
//    TextSwitcher
////////////////////////////////
// Set the factory used to create TextViews to switch between.
mSwitcher.setFactory(mFactory);
private ViewFactory mFactory = new ViewFactory() {

        @Override
        public View makeView() {

            // Create a new TextView
            TextView t = new TextView(MainActivity.this);
            t.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
            t.setTextAppearance(MainActivity.this, android.R.style.TextAppearance_Large);
            return t;
        }
};
    
Animation in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
Animation out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
mSwitcher.setInAnimation(in);
mSwitcher.setOutAnimation(out);

////////////////////////////////
//    SlidingTabsColors
////////////////////////////////
public class SlidingTabLayout extends HorizontalScrollView 

////////////////////////////////
//    PreferenceActivity 
// http://blog.csdn.net/dawanganban/article/details/19082949
////////////////////////////////
public class MainActivity extends PreferenceActivity {  
  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        //setContentView(R.layout.activity_main);  
        addPreferencesFromResource(R.xml.preference);  
    }  
} 

// <?xml version="1.0" encoding="UTF-8"?>  
// <PreferenceScreen   
//     xmlns:android="http://schemas.android.com/apk/res/android"   
//     android:title="Settings">  
//     <CheckBoxPreference   
//         android:title="Love me?"   
//         android:summaryOn="Yes,I love you!"  
//         android:summaryOff="No,I am sorry."  
//         android:defaultValue="true">  
//     </CheckBoxPreference>  
// </PreferenceScreen> 


// <?xml version="1.0" encoding="UTF-8"?>  
// <PreferenceScreen   
//     xmlns:android="http://schemas.android.com/apk/res/android"   
//     android:title="Settings">  
//     <PreferenceScreen   
//         xmlns:android="http://schemas.android.com/apk/res/android"  
//         android:title="Emotions"  
//         android:summary="settings about emotions">  
//         <CheckBoxPreference    
//             android:title="Love me?"   
//             android:summaryOn="Yes,I love you!"  
//             android:summaryOff="No,I am sorry."  
//             android:defaultValue="true">  
//         </CheckBoxPreference>  
//         <CheckBoxPreference   
//             android:title="Hate me?"   
//             android:summaryOn="Yes,I hate you!"  
//             android:summaryOff="No,you are a good person."  
//             android:defaultValue="false">  
//         </CheckBoxPreference>       
//     </PreferenceScreen>  
//     <PreferenceScreen   
//         xmlns:android="http://schemas.android.com/apk/res/android"  
//         android:title="Relations"  
//         android:summary="settings about relations">  
//         <CheckBoxPreference   
//             android:title="Family?"   
//             android:summaryOn="Yes,we are family!"  
//             android:summaryOff="No,I am sorry."  
//             android:defaultValue="true">  
//         </CheckBoxPreference>  
//         <CheckBoxPreference   
//             android:title="Friends?"   
//             android:summaryOn="Yes,we are friends!"  
//             android:summaryOff="No,I am sorry."  
//             android:defaultValue="false">  
//         </CheckBoxPreference>       
//     </PreferenceScreen>     
// </PreferenceScreen> 

<?xml version="1.0" encoding="utf-8"?>  
<preference-headers   
    xmlns:android="http://schemas.android.com/apk/res/android">  
    <!-- 指定启动指定PreferenceFragment的列表项 -->  
    <header android:fragment=  
        "org.crazyit.app.PreferenceActivityTest$Prefs1Fragment"  
        android:icon="@drawable/ic_settings_applications"  
        android:title="程序选项设置"  
        android:summary="设置应用的相关选项" />  
    <!-- 指定启动指定PreferenceFragment的列表项 -->   
    <header android:fragment=  
        "org.crazyit.app.PreferenceActivityTest$Prefs2Fragment"  
        android:icon="@drawable/ic_settings_display"  
        android:title="界面选项设置 "  
        android:summary="设置显示界面的相关选项">  
        <!-- 使用extra可向Activity传入额外的数据 -->  
        <extra android:name="website"  
            android:value="www.crazyit.org" />  
    </header>  
    <!-- 使用Intent启动指定Activity的列表项 -->  
    <header  
        android:icon="@drawable/ic_settings_display"  
        android:title="使用Intent"  
        android:summary="使用Intent启动某个Activity">  
        <intent  android:action="android.intent.action.VIEW"  
            android:data="http://www.crazyit.org" />  
    </header>  
</preference-headers>  


public void onBuildHeaders(List<Header> target)  
{  
    // 加载选项设置列表的布局文件  
    loadHeadersFromResource(R.xml.preference_headers, target);  
} 

public class PreferenceActivityTest extends PreferenceActivity  
{  
    @Override  
    protected void onCreate(Bundle savedInstanceState)  
    {  
        super.onCreate(savedInstanceState);  
        // 该方法用于为该界面设置一个标题按钮  
        if (hasHeaders())  
        {  
            Button button = new Button(this);  
            button.setText("设置操作");  
            // 将该按钮添加到该界面上  
            setListFooter(button);  
        }  
    }  
    // 重写该该方法，负责加载页面布局文件  
    @Override  
    public void onBuildHeaders(List<Header> target)  
    {  
        // 加载选项设置列表的布局文件  
        loadHeadersFromResource(R.xml.preference_headers, target);  
    }  
  
    public static class Prefs1Fragment extends PreferenceFragment  
    {  
        @Override  
        public void onCreate(Bundle savedInstanceState)  
        {  
            super.onCreate(savedInstanceState);  
            addPreferencesFromResource(R.xml.preferences);  
        }  
    }  
    public static class Prefs2Fragment extends PreferenceFragment  
    {  
        @Override  
        public void onCreate(Bundle savedInstanceState)  
        {  
            super.onCreate(savedInstanceState);  
            addPreferencesFromResource(R.xml.display_prefs);  
            // 获取传入该Fragment的参数  
            String website = getArguments().getString("website");  
        }  
    }     
}  


CardView
Shadow: view.setOutline()
Shape: view.clipToOutline()

Ripples
android:foreground="?android:attr/selectableItemBackground"

public boolean onTouchEvent(MotionEvent event){
	switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			animate().setDuration(100).scaleX(1.2f).scaleY(1.2f).translationZ(20);
			return true;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			animate().setDuration(100).scaleX(1).scaleY(1).translationZ(0);
			return true;
	}
}

<ImageButton

android:background=""
android:src=""

android:elevation="4dp"
android:stateListAnimator="@anim/button_raise" />

<selector>
<item
	android:state_enabled="true"
	android:state_pressed="true">
	<objectAnimator
		android:duration="@android:integer/config_shortAnimTime"
		android:propertyName="translationZ"
		android:valueFrom="@dimen/button_elevation"
		android:valueTo="@dimen/button_press_elevation"
		android:valueType="floatType" />
</item>
<item>
	<objectAnimator
		android:duration="@android:integer/config_shortAnimTime"
		android:propertyName="translationZ"
		android:valueFrom="@dimen/button_press_elevation"
		android:valueTo="@dimen/button_elevation"
		android:valueType="floatType"/>
		
</item>
</selector>


Theme colors
<style name="BaseAppTheme" parent="android:Theme.Material.Light">
	<item name="android:colorPrimary">#xxx</item>
	<item name="android:navagationBarColor">?android:colorPrimarDark</item>
	<item name="android:statusBarColor">?android:colorPrimaryDark</item>
	<item name="android:windowBackground">?android:colorBackground</item>
</style>

final ImageView hero = (ImageView) findViewById(R.id.photo);
ObjectAnimator color = ObjectAnimator.ofArgb(hero.getColorFilter(),"color",getResources().getColor(R.color.photo_tint));
color.addUpdateListener(new ValueAnimator.AnimatorUpdatedListener(){
	public void onAnimationUpdate(ValueAnimator valueAnimator){
		hero.getDrawable().setColorFilter(hero.getColorFilter());
	}
});


private void colorize(Bitmap photo){
	Palette palette = Palette.generate(photo);
	applyPalette(palette);
}

private void applyPalette(Palette palette){
	getWindow().setBackgroundDrawable(new ColorDrawable(palette.getDarkMutedColor().getRgb()));
	
	titleview.setTextColor(palette.getVibrantColor().getRgb());
	
	descriptionView.setTextColor(palette.getLightVirantColor().getRgb());
}

// Ripple button
<ripple android:color="?android:colorControlHightlight">
	<item>
		<shape android:shape="oval">
			<solid android:color="?android:colorAccent"/>
		</shape>
	</item>
</ripple>

private void colorRipple(int id, int bgColor, int tintColor){
	View buttonView = findViewById(id);
	
	RippleDrawable ripple = (RipperDrawable)buttonView.getBackground();
	GradientDrawable rippleBackground = (GradientDrawable) ripple.getDrawable(0);
	rippleBackground.setColor(bgColor);
	ripple.setColor(ColorStateList.valueOf(tintColor));
	
}

// Reveal
ViewAnimationUtils.createCircularReveal();
int cx = (button.getLeft() + buttonn.getRight()) /2;
int cy = (button.getTop() + button.getBottom)) /2;

float radius = Math.max(map.getWidth(), map.getHight()) * 2.0f;

if(map.getVisibility() == View.INVISIBLE){
	map.setViewsibilty(View.VISIBLE);
	ViewAnimationUtils.createCircularReval(map,cx,cy,0,radius).start();
}else{
	ValueAnimator reveal = ViewAnimationUtils.createCircularReval(map,cx,cy,radius,0);
	reveal.addListener(new  AnimatorListenerAdapter(){
		public void onAnimationEnd(Animator animation){
			map.setVisibility(View.INVISIBLE);
		}
	})
	reveal.start();
}

// ViewCompat 使用

ViewCompat.setTransitionName(title, getString(R.string.transition_title));
ViewCompat.setTransitionName(date, getString(R.string.transition_date));
ViewCompat.setTransitionName(webView, getString(R.string.transition_body));

Intent detailIntent = new Intent(FeedListActivity.this,FeedDetailActivity.class);
detailIntent.putExtra(FeedDetailActivity.ARR_ITEM,item);
FeedAdapter.ViewHolder viewHolder = (FeedApdater.ViewHolder)recyclerView.findViewHodlerForItemId(item.getPubDate());
String titleName = getString(R.string.transition_titile);
// ...

Pair<View,String> titlePair = Pair.create(viewHolder.getTitleView(),titleName);
// ...

ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
	this,
	titilePair,
	datePair,
	bodyPair);
ActivityCompat.startActivity(this,detailIntent,options.toBundle());

public boolean onOptionsItemSelected(MenuItem menuItem) {
    int id = menuItem.getItemId();
    if (id == android.R.id.home) {
        ActivityCompat.finishAfterTransition(this);
        return true;
    }
    return super.onOptionsItemSelected(menuItem);
}



