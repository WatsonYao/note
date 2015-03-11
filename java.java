//数据 与 显示 分开

//工厂模式：
//将大量有共同接口的类实例化。
//比如会飞的动物这种；

// Simple Factory == Static Factory Method
// Factory Method == Polymorphic Factory == Virtual Constructor
// Abstract Factory == Kit == ToolKit

// 创建模式
// 对类的实例化过程的抽象化
// 一些系统在创建对象时，需要动态地决定怎样创建对象，创建哪些对象，以及如何组合表示这些对象
// 创建模式表述了 怎样构造和封装这些动态的决定

// 创建模式分为 类的 和 对象的 创建模式
/**
 * 类的创建模式
 * 类的创建模式使用继承关系，把类的创建延迟到子类，
 * 从而封装了客户端将得到哪些具体类的信息，并且隐藏了这些类的实例是如何被创建和放在一起的
 * 
 * 对象的创建模式
 * 把对象的创建过程动态的委派给另一个对象，从而动态地决定客户端将得到哪些具体的实例，以及...
 * 
 **/

// 结构模式
// 如何将类或对象结合在一起形成更大的结构
// 类的 和 对象的 两种




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

// 原始模型 模式
// 简单 和 登记

public class Client{

	private Prototype ptototype;

	public void operation(Prototype example){
		Prototype p = (Prototype) example.clone();

	}
}

public interface Prototype extends Cloneable{
	Prototype clone();
}

public class ConcretePrototype implements Prototype{
	public Object clone(){
		try{
			return super.clone();
		}catch(){
			return null;
		}
	}
}

// 登记式 保存到原型管理器中
public interface Prototype extends Cloneable{
	public Object clone();
}

public class ConcretePrototype implements Prototype{

	public synchronized Object clone(){
		Prototype temp = null;
		try{
			temp = (Prototype)super.clone();
			return temp;
		}catch(){

		}finally{
			return temp;
		}
	}
}

public class PrototypeManager{
	private Vector objects = new Vector();

	public void add(Prototype object){
		objects.add(object);
	}

	public Prototype get(int i){
		return (Prototype)objects.get(i);
	}

	public int getSize(){
		return objects.size();
	}
}

public class Client{
	private PrototypeManager mgr;
	private Prototype prototype;

	public void registerPrototype(){
		prototype = new ConcretePrototype();
		Prototype copytype = (Prototype) prototype.clone();
		mgr.add(copytype);
	}
}

// 适配器模式
// 把一个类的接口变换成客户端所能期待的另一种接口。使两个类能够在一起工作！
// 类的 和 对象的 两种
// 适配器角色 扩展了adaptee，同时又实现了target
// 对象适配器模式不是使用继承关系连接到adaptee类，而是使用委派关系连接到adaptee类

// 缺省适配模式


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

//  RecyclerView does provide an OnItemTouchListener
mRecyclerView = (RecyclerView)v.findViewById(R.id.xxx);
mRecyclerView.setLayoutManager(new LinearLayoutManger(getActivity()));
mRecyclerView.setAdapter(new CrimeAdapter());

// viewholder 传一个view进去固定控件，绑定数据与view的显示关系
private class CrimeHolder extends ViewHolder implements View.OnClickListener{
	private final CheckBox mSolvedCheckBox;
	private Crime mCrime;

	public CrimeHolder(View itemView){
		super(itemView);
		itemView.setOnClickListener(this);

		mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.xxx);
	}

	public void bindCrime(Crime crime){
		mCrime = crime;
		mSolvedCheckBox.setChecked(crime.isSolved());

		boolean isSelected = mMultiSelector.isSelected(getPositionin());
		itemView.setActivated(isSelected);
	}

	public void onClick()
}

private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{

	public CrimeHolder onCreateViewHolder(ViewGroup parent,int pos){
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.xxx,parent,false);
		return new CrimeHolder(view);
	}

	public void onBindViewHolder(CrimeHolder holder,int pos){
		Crime crime = mCrimes.get(pos);
		holder.bindCrime(crime);
	}

	public int getItemCount(){
		return mCrimes.size();
	}
}

//建造模式

public class Director{

	private Builder builder;
	
	public void construct(){
		builder = new ConcreteBuilder();
		builder.buildPart1();
		builder.buildPart2();
		builder.retrieveResult();
	}
}

abstract public class Builder{

	public abstract void builderPart1();
	public abstract void builderPart2();

	public abstract Product retrieveResult();
}

public class ConcreteBuilder extends Builder{

	private Product product = new Product();

	public Product retrieveResult(){
		return product;
	}

	public void builderPart1(){

	}

	public void builderPart2(){
		
	}
}


mMainToolbar = (Toolbar) this.findViewById(R.id.main_bar);
this.setSupportActionBar(mMainToolbar);

mMainListView = (ListView) this.findViewById(R.id.main_list_view);
final View header = LayoutInflater.from(this).inflate(R.layout.layout_header,null);
mMainListView.addHeaderView(header);
mMainListView.setAdapter(new XXXAdapter(this));

mMainListView.setOnTouchListener(new View.OnTouchListener(){

	public boolean onTouch(View v, MotionEvent event){
		final float y = event.getY();
		float translationY = mMationToolbar.getTranslationY();

		switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
			mStartY = y;
			mLastY = mStartY;
			break;
			case MotionEvent.ACTION_MOVE:
			float mDeltaY = y - mLastY;

			float newTansY = translationY + mDeltaY;

			if(newTansY <=0 && newTansY >= -mMationToolbar.getHeight()){
				mMationToolbar.setTranslationY(newTansY);
			}

			mLastY = y;
			mLastDeltaY = mDeltaY;

			break;
			case MotionEvent.ACTION_UP:
			ObjectAnimator animator = null;
			if(mLastDeltaY <0 && mMainListView.getFirstVisibilePosition() > 1){
				ainmator = ObjectAnimator.ofFloat(mMationToolbar,"translationY",mMainToolbar.getTranslationY(),-mMationToolbar.getHeight());
			}else{
				animator = ObjectAnimator.ofFloat(mMationToolbar,"translationY",mMationToolbar.getTranslationY(),0);
			}

			animator.setDuration(100);
			animator.start();
			animator.setInterpolator(AnimationUtils.loadInterpolator(MainActivity.this, android.R.interpolator.linear));
			break;
		}
		return false;
	}
});

Toolbar actionBarToolBar = (Toolbar)findViewById(R.id.xxx);

setSupportActionBar(actionBarToolBar);
actionBarToolBar.setNavigationIcon(R.drawable.xxx);
actionBarToolBar.setNavigationContentDescription(getResources().getString(R.string.xxx));
actionBarToolBar.setLogo(R.drawable.xxx);
actionBarToolBar.setLogoDescription(getResources().getString(R.string.xxx));

// 单纯的toolbar
toolbar = (Toolbar)findViewById(R.id.xxx);
toolbar.setLogo(R.drawable.xxx);
toolbar.setLogoDescription(getResources().getString(R.string.xxx));
toolbar.setSubtitle("xxx");
toolbar.setSubtitleTextColor(getResources().getColor(R.color.xxx));
toolbar.setTilte("xxx");
toolbar.setTitleTextColor(getResources().getColor(R.color.xxx));
toolbar.setnavigationIcon(R.drawable.xxx);
toolbar.setNavigationContentDescription(getResources().getString(R.string.xxx));
toolbar.inflateMenu(R.menu.xxx);
toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){

	public boolean onMenuItemClick(MenuItem menuItem){
		// xxx;
		return true;
	}
});
toolbar.setNavigationOnClickListener(new View.OnClickListener(){

	public void onClick(View v){
		// xxx'
		toolbar.setVisibility(View.GONE);
	}
});

// 事件传递
//ViewGroup里的onInterceptTouchEvent默认值是false这样才能把事件传给View里的onTouchEvent.
//ViewGroup里的onTouchEvent默认值是false。
//View里的onTouchEvent返回默认值是true.这样才能执行多次touch事件。

/**
每个事件都是以 ACTION_DOWN 开始 和 ACTION_UP 结束
对事件的处理包括三类：
1. dispatchTouchEvent() 传递
2. onInterceptTouchEvent() 拦截
3. onTouchEvent() OnTouchListener 消费

传递流程：
1. 事件从activity.dispatchTouchEvent() 开始传递，
只要没有被停止或拦截，从最上层的ViewGroup开始一直往下传递。
下层的view可以用过onTouchEvent()对事件进行处理

2. 事件由父ViewGroup传递给子view，ViewGroup可以通过onInterceptTouchEvent对事件做拦截，停止其往下传递

3. 如果事件从上往下传递过程中一直没有被停止，且最底层子View没有消费事件，事件会反向往上传递，
这时父ViewGroup可以进行消费，如果还是没有被消费的话，最后会到activity的onTouchEvent()函数

4. 如果View没有对ACTION_DOWN进行消费，之后的其他事件也不会传递过来

5. OnTouchListener优先于onTouchEvent()对事件进行消费

消费即表示相应函数返回值为true
**/


// 绘图过程
performMeasure(int childWidthMeasureSpec, int childHeightMeasureSpec){
	//...
	mView.measure(childWidthMeasureSpec,childHeightMeasureSpec);
	//...
}

// measurer
public final void measure(int widthMeasureSpec, int heightMeasureSpec){
	// ...
	onMeasure(widthMeasureSpec,heightMeasureSpec);
	// ...
}

// onMeasure
// 设置自己所需要的大小
protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
	setMeasureDimension(
		getDefaultSize(getSuggestedMinimumWidth(),widthMeasureSpec),
		getDefaultSize(getSuggestedMinimumHeight(),heightMeasureSpec)
		);
}

// setMeasureDimension()
// 对两个成员变量进行赋值
protected final void setMeasureDimension(int measuredWidth, int measureHeight){
	// ...

	mMeasureWidth = measuredWidth;
	mMeasureHeight = measureHeight;

	// ...
}

// Layout过程 布局过程

private void performLayout(WindowManager.LayoutParams lp, int desiredWindowWidth, int desiredWindowHeight){
	// ...
	host.layout(0,0,host.getMeasureWidth(),host.getmeasuredHight);
	// ...
}

public void layout( int l , int t, int r, int b){
	//onMeasure(mOldWidthMeasureSpec, mOldHeightMeasureSpec);
	setFrame();
	// ...
	onLayout(changed,l,t,r,b);
	// ...

}

// view真正渲染到屏幕上的矩形区域
protected boolean setFrame(int left, int top, int right, int bottom){

}

// measure出来的宽高是该控件期望得到的尺寸，真正显示到屏幕上面的位置和大小由layout方法来决定。

// 要实现一个ViewGroup的话，你只需要在onLayout() 方法中遍历的调用子控件的onLayout()方法就行了，
// 需要做的就是把lrtb这四个值算好

// 自绘控件
// 继承view，重写onDraw方法，在布局文件里面引用
public class MyRingView extends View{

	private static final int FLUSH = 0;

	public MyRingView(Context context, AttributeSet attrs){
		super(context, attrs);
	}

	private float radius;

	private Paint paint;

	private void initView(){
		radius = 0;
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(0);
		paint.setAlpha(255);
		paint.setColor(Color.RED);

		invalidate();
	}

	private Handle handler = new Handler(){
		public void handleMessage(android.os.Message msg){
			radius += 5;

			paint.setStrokeWidth(radius/3);
			int alpha = paint.getAlpha();
			alpha -= 10;

			if(alpha <10){
				alpha = 0;
			}

			paint.setAlpha(alpha);
			// 刷新视图
			invalidate();
		}
	}

	protected void onDraw(Canvas canvas){
		if(paint == null){
			return;
		}

		canvas.drawCircle(cx, cy, radius, paint);

		if(paint.getAlpha() >0){
			handler.sendEmptyMessageDelayed(FLUSH,100);
		}
	}


	private int cx;
	private int cy;

	public boolean onTouchEvent(MotionEvent event){
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			cx = (int) event.getX();
			cy = (int) event.getY();
			initView();
		}

		return super.onTouchEvent(evnet);
	}

	protected void onMeasure(int widthMeasureSpce, int heightMeasureSpec){
		super.onMeasure(widthMeasureSpec,heightMeasureSpec);
	}
}


public class SlidingTabsBasicFragment extends Fragment{

	private SlidingTabLayout mSlidingTabLayout;
	private ViewPager mViewPager;

	public void onViewCreated(View view, Bundle savedInstanceState){

		mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
		mViewPager.setAdapter(new SamplePagerAdapter());

		mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
		mSlidingTabLayout.setViewPager(mViewPager);
	}

	class SamplePagerAdapter extends PagerAdapter{

		public int getCount(){
			return 10;
		}

		public boolean isViewFromObject(View view, Object o){
			return o == view;
		}

		public CharSequence getPageTitle(int position){
			return "Item" + (position + 1);
		}

		public Object instantiateItem(ViewGroup container, int position){
			View view = getActivity().getLayoutInflater().inflate(R.layout.pager_item,container,false);

			container.addView(view);

			TextView title = (TextView) view.findViewById(R.id.item_title);
			title.setText(String.valueOf(position + 1));

			return view;
		}

		public void destroyItem( ViewGroup container, int position, Object object){
			container.removeView((View)object);
		}
	}
}

public class SlidingTabLayout extends HorizontalScrollView{

	public interface TabColorizer{

		int getIndicatorColor(int position);

		int getDividerColor(int position);
	}

	private static final int TITLE_OFFSET_DIPS =24;
	private static final int TAB_VIEW_PADDING_DIPS = 16;
	private static final int TAB_VIEW_TEXT_SIZE_SP = 12;

	private int mTitleOffset;

	private int mTabViewLayoutId;
	private int mTabViewTextViewId;

	private ViewPager mViewPager;
	private ViewPager.OnPageChangeListener mViewPagerPageChangeListener;

	private final SlidingTabStrip mTabStrip;

	private SlidingTabLayout(Context context){
		this(context, null);
	}

	public SlidingTabLayout(Context context, AttributeSet attrs){
		this(context, null);
	}

	public SlidingTabLayout(Context context, AttributeSet attrs){
		this(context, attrs, 0);
	}

	public SlidingTabLayout(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);

		setHorizontalScrollBarenabled(false);
		setFillViewport(true);

		mTitleOffset = (int)(TITLE_OFFSET_DIPS * getResources().getDisplayMetrics().density);

		mTabStrip = new SlidingTabStrip(context);

		addView(mTabStrip, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	}

	public void setCustomTabColorizer(TabColorizer tabColorizer){
		mTabStrip.setCustomTabColorizer(tabColorizer);
	}

	public void setSelectedIndicatorColors(int... colors){
		mTabStrip.setSelectedIndicatorColors(colors);
	}

	public void setDividerColors(int... colors){
		mTabStrip.setDividerColors(colors);
	}

	// ...

	public void setViewPager(ViewPager viewPager){
		mTabStrip.removeAllViews();

		mViewPager = viewPager;
		if(viewPager != null){
			viewPager.setOnPageChangeListener(new InternalViewPagerListener());
			populateTabStrip();
		}

	}

	protected TextView createDefaultTabView(Context context){
		TextView textView = new TextView(context);
		textView.setGravity(Gravity.CENTER);
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,TAB_VIEW_TEXT_SIZE_SP);
		textView.setTypeface(Typeface.DEFAULT_BOLD);

		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			TypedValue outValue = new TypedValue();
			getConctext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground,outValue,true);
			textView.setBackgroundResource(outValue.resourceId);
		}

		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
			textView.setAllCaps(true);
		}

		int padding = (int) (TAB_VIEW_PADDING_DIPS * getResources().getDisplayMetrics().density);
		textView.setPadding(padding,padding,padding,padding);

		return textView;
	}

	private void populateTabStrip(){
		final PagerAdapter adapter = mViewPager.getAdapter();
		final View.OnClickListener tabClickListener = new TabClickListener();

		for( int i= 0; i< adapter.getCount(); i++){
			View tabView = null;
			TextView tabTitleView = null;

			if(mTabViewLayoutId != 0){
				tabView = LayoutInflater.from(getContext()).inflate(mTabViewLayoutId,mTabStrip,false);
				tabTitleView = (TextView) tabView.findViewById(mTabViewTextViewId);
			}

			if(tabView == null){
				tabView = createDefaultTabView(getContext());
			}

			if(tabTitleView == null && TextView.class.isInstance(tabView)){
				tabTitleView = (TextView) tabView;
			}

			tabTitleView.setText(adapter.getPageTitle(i));
			tabView.setOnclickListener(tabClickListener);

			mTabStrip.addView(tabView);
		}
	}

	protecetd void onAttachedToWindow(){
		super.onAttachToWindow();

		if(mViewPager != null){
			scrollToTab(mViewPager.getCurrentItem(),0);
		}
	}

	private void scrollToTab(int tabIndex, int positionOffset){
		final int tabStripChildCount = mTabStrip.getChildCount();
		if(tabStripChildCount == 0 || tabIndex <0 || tabIndex >= tabStripChildCount){
			return;
		}

		View selectedChild = mTabStrip.getChildAt(tabIndex);
		if(selectedChild != null){
			int targetScrollX = selectedChild.getLeft() + positionOffset;

			if(tabIndex > 0 || positionOffset > 0){
				targetScrollX -= mTitleOffset;
			}

			scrollTo(targetScrollX, 0);
		}
	}

	private class InternalViewPagerListener implements ViewPager.OnPageChangeListener{

		private int mScrollState;

		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){

			int tabStripChildCount = mTabStrip.getChildCount();
			if((tabStripChildCount == 0) || (positioni < 0) || (position >= tabStripChildCount)){
				return;
			}

			mTabStrip.onViewPagerChanged(position, positionOffset);

			View selectedTitle = mTabStrip.getChildAt(position);
			int extraOffset = (selectedTitle != null)
			? (int)(positionOffset * selectedTitle.getWidth())
			: 0;
			scrollToTab(position, extraOffset);

			if(mViewPagerPageChangeListener != null){
				mViewPagerPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
			}
		}

		public void onPageScrollStateChanged(int state){
			mScrollState = state;

			if(mViewPagerPageChangeListener != null){
				mViewPagerPageChangeListener.onPageScrollStateChanged(state);
			}
		}

		public void onPageSelected(int position){
			if(mScrollState == ViewPager.SCROLL_STATE_IDLE){
				mTabStrip.onViewPagerPageChanged(position, 0f);
				scrollToTab(position, 0);
			}

			if(mViewPagerPageChangeListener != null){
				mViewPagerPageChangeListener.onPageSelected(position);
			}
		}
	}

	private class TabClickListener implements View.OnClickListener{
		public void onClick(View v){
			for(int i=0; i<mTabStrip.getChildCount(); i++){
				if( v == mTabStrip.getChildAt(i)){
					mViewPager.setCurrentItem(i);
					return ;
				}
			}
		}
	}

}

class SlidingTabStrip extends LinearLayout{

	private SlidingTabLayout.TabColorizer mCustomTabColorizer;
	private final SimpleTabColorizer mDefaultTabColorizer;

	SlidingTabStrip(Context context){
		this(context, null);
	}

	SlidingTabStrip(Context context, AttributeSet attrs){
		super(context, attrs);
		setWillNotDraw(false);

		final float desity = getResources().getDisplayMetrics().density;

		TypedValue outValue = new TypedValue();
		context.getTheme().resolveAttribute(R.attr.colorForeground, outValue, true);
		final int themeForegroundColor = outValue.data;

		mDefaultBottomBorderColor = setColorAlpa(themeForegroundColor,DEFAULT_BOTTOM_BORDER_COLOR_ALPHA);

		mDefaultTabColorizer = new SimpleTabColorizer();
		mDefaultTabColorizer.setIndicatorColors(DEFAULT_SELLECTED_INDICATOR_COLOR);
		mDefaultTabColorizer.setDividerColors(setColorAlpha(themeForegroundColor,DEFAULT_DIVIDER_COLOR_ALPHA));

		mBottomBorderThickness = (int)(DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS * density);
		mBottomBorderPaint = new Paint();
		mBottomBorderPaint.setColor(mDefaultBottomBorderColor);

		mSelectedIndicatorThickness = (int) (SELECTED_INDICATOR_THICKNESS_DIPS * density);
		mselectedIndicatorPaint = new Paint();

		mDividerHight = DEFAULT_DIVIDER_HEIGHT;
		mDividerPaint = new Paint();
		mDividerPaint.setStokeWidth((int)(DEFAULT_DIVIDER_THICKNESS_DIPS * density));
	}

	void setCustomTabColorizer(SlidingTabLayout.TabColorizer, customTabColorizer){
		mCustomTabColorizer = customTabColorizer;
		invalidate();
	}

	void setSelectedIndicatorColors(int... colors){
		mCustomTabColorizer = null;
		mDefaultTabColorizer.setIndicatorColors(colors);
		invalidate();
	}

	void setDividerColors(int... colors){
		mCustomTabColorizer = null;
		mDefaultTabColorizer.setDividerColors(colors);
		invalidate();
	}

	void onViewPagerChanged(int position, float positionOffset){
		mSelectedPosition = position;
		mSelectionOffset = positionOffset;
		invalidate();
	}

	protected void onDraw(Canvas canvas){
		final int height = getHeight();
		final int childCount = getChildCount();
		final int mDividerHightPx = (int)(Math.min(Math.max(0f,mDividerHight),1f) * height);

		final SlidingTabLayout.TabColorizer tabColorizer = mCustomTabColorizer != null
		? mCustomTabColorizer
		: mDefaultTabColorizer;

		if(childCount > 0){
			View selectedTitle = getChildAt(mSelectedPosition);

			int left = selectedTitle.getLeft();
			int right = selectedTitle.getRight();
			int color = tabColorizer.getIndicatorColor(mSelectedPosition);

			if(mSelectionOffset > 0f && mSelectedPosition < (getChildCount() - 1)){
				int nextColor = tabColorizer.getIndicatorColor(mSelectedPosition + 1);
				if(color != nextColor){
					color = blendColors(nextColor,color,mSelectionOffset);
				}

				View nextTitle = getChildAt(mSelectedPosition + 1);
				left = (int)(mSelectionOffset * nextTitle.getLeft() + (1.0f - mSelectionOffset * left);
					right = (int)(mSelectionOffset * nextTitle.getRight() + (1.0f -mSelectionOffset * right));
				}

				mselectedIndicatorPaint.setColor(color);

				canvas.drawRect(left, height - mSelectedIndicatorThickness, right, height, mselectedIndicatorPaint);
			}

			canvas.drawRect(0, height - mBottomBorderThickness, getWidth(),height, mBottomBorderPaint);

			int separatorTop = (height - dividerHieghtPx ) / 2;
			for( int i=0; i<childCount -1; i++){
				View child = getChildAt(i);
				mDividerPaint.setColor(tabColorizer.getDividerColor(i));
				canvas.drawLine(child.getRight(),separatorTop, child.getRight(),
					separatorTop + dividerHieghtPx, mDividerPaint);
			}

		}

		private static int setColorAlpha( int color, byte alpha){
			return Color.argb(alpha, Color.red(color),Color.green(color),Color.blue(color));
		}

		private static int blendColors(int color1, int color2, float ratio){
			final float inverseRation = 1f - ratio;

			float r = (Color.red(color1)* ratio) + (Color.red(color2) * inverseRation);
			float g = (Color.green(color1) * ratio) + (Color.green(color2) * inverseRation);
			float b = (Color.blue(color1) * ratio) +　(Color.blue(color2) * inverseRation);

			return Color.rgb((int)r, (int)g, (int)b);
		}

		private static class SimpleTabColorizer implments SlidingTabLayout.TabColorizer{
			private int[] mIndicatorColors;
			private int[] mDividerColors;

			public final int getindicatorColor(int position){
				return mIndicatorColors[position % mIndicatorColors.length];
			}

			public final int getDividerColor(int position){
				return mDividerColors[position % mDividerColors.length];
			}

			void setIndicatorColors(int... colors){
				mIndicatorColors = colors;
			}

			void setDividerColors(int... colors){
				mDividerColors = colors;
			}

		}
	}

// ListView 保存滑动状态
	private Parcelable mListState = null;
	private static final String LIST_STATE_KEY = "liststate";

	protected void onSaveInstanceState(Bundle icicle){
		super.onSaveInstanceState(icicle);

		if(mList != null){
			icicle.putParcelable(LIST_STATE_KEY,mList.onSaveInstanceState());
		}
	}

	protected void onRestoreInstanceState(Bundle icicle){
		super.onRestoreInstanceState(icicle);
		mListState = icicle.getParcelable(LIST_STATE_KEY);
	}

	if(mListState != null){
		mList.onRestoreInstanceState(mListState);
		mListState = null;
	}

// tools
	public class LUtils{

		protected ActionBarActivity mActivity;

		private LUtils(ActionBarActivity activity){
			mActivity = activity;
		}

		public static LUtils getInstance(ActionBarActivity activity){
			return new LUtils(activity);
		}

		private static boolean hasL(){
			return Build.VERSION.SDK_INT >= Build.VERSION.CODES.LOLLIPOP;
		}

		public void startActivityWithTransition(Intent intent, final View clickedView, final String transtionName){
			ActivityOptions options = null;
			if(hasL() && clickedView != null && !TextUtils.isEmpty(setTransitionName)){

			}

			mActivity.startActivity(intent, (options!=null) ? options.toBundle():null);
		}
	}

	public class PrefUtils{

		public static void init(final Contenxt context){
			SharedPreferences sp = PreferenceManager.getDefaultSharePreferences(context);
		// ...
		}
	}

	public class ScrimInsetsScrollView extends ScrollView{

		private Drawable mInsetForeground;

		private Rect mInsets；
		private Rect mTempRect = new Rect();
		private OnInsetsCallback mOnInsetsCallback;

		public ScrimInsetsScrollView(Context context) {
			super(context);
			init(context, null, 0);
		}

		public ScrimInsetsScrollView(Context context, AttributeSet attrs) {
			super(context, attrs);
			init(context, attrs, 0);
		}

		public ScrimInsetsScrollView(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			init(context, attrs, defStyle);
		}

		private void init(Context context, AttributeSet attrs, int defStyle){
			final TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.ScrimInsetsView, defStyle, 0);
			if (a == null) {
				return;
			}
			mInsetForeground = a.getDrawable(R.styleable.ScrimInsetsView_insetForeground);
			a.recycle();

			setWillNotDraw(true);
		}

		protected boolean fitSystemWindows(Rect insets) {
			mInsets = new Rect(insets);
			setWillNotDraw(mInsetForeground == null);
			ViewCompat.postInvalidateOnAnimation(this);
			if (mOnInsetsCallback != null) {
				mOnInsetsCallback.onInsetsChanged(insets);
			}
        return true; // consume insets
    }

    public void draw(Canvas canvas){
    	super.draw(canvas);

    	int width = getWidth();
    	int height = getHeight();

    	if (mInsets != null && mInsetForeground != null) {
    		int sc = canvas.save();
    		canvas.translate(getScrollX(),getScrollY());
    	}
    }
}

protected void onNavDrawerStateChanged(boolean isOpen,boolean isAnimation){
	if(mActioinBarAutoHideEnabled && isOpen){
		autoShowOrHideActionBar(true);
	}
}

protected void autoShowOrHideActionBar(boolean show) {
	if (show == mActionBarShown) {
		return;
	}

	mActionBarShown = show;
	onActionBarAutoShowOrHide(show);
}

protected void registerHideableHeaderView(View hideableHeaderView) {
	if (!mHideableHeaderViews.contains(hideableHeaderView)) {
		mHideableHeaderViews.add(hideableHeaderView);
	}
}

protected void deregisterHideableHeaderView(View hideableHeaderView) {
	if (mHideableHeaderViews.contains(hideableHeaderView)) {
		mHideableHeaderViews.remove(hideableHeaderView);
	}
}

protected void onActionBarAutoShowOrHide(boolean shown) {
	if (mStatusBarColorAnimator != null) {
		mStatusBarColorAnimator.cancel();
	}
	mStatusBarColorAnimator = ObjectAnimator.ofInt(
		(mDrawerLayout != null) ? mDrawerLayout : mLUtils,
		(mDrawerLayout != null) ? "statusBarBackgroundColor" : "statusBarColor",
		shown ? Color.BLACK : mNormalStatusBarColor,
		shown ? mNormalStatusBarColor : Color.BLACK)
	.setDuration(250);
	if (mDrawerLayout != null) {
		mStatusBarColorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				ViewCompat.postInvalidateOnAnimation(mDrawerLayout);
			}
		});
	}
	mStatusBarColorAnimator.setEvaluator(ARGB_EVALUATOR);
	mStatusBarColorAnimator.start();

	updateSwipeRefreshProgressBarTop();

	for (View view : mHideableHeaderViews) {
		if (shown) {
			view.animate()
			.translationY(0)
			.alpha(1)
			.setDuration(HEADER_HIDE_ANIM_DURATION)
			.setInterpolator(new DecelerateInterpolator());
		} else {
			view.animate()
			.translationY(-view.getBottom())
			.alpha(0)
			.setDuration(HEADER_HIDE_ANIM_DURATION)
			.setInterpolator(new DecelerateInterpolator());
		}
	}
}


public abstract class BaseActivity extends ActionBarActivity implments 
SharedPreferences.OnSharedPreferenceChangeListener,
MultiSwipeRefreshLayout.CanChildScrollUpCallback{

	protected void onCreate(Bundle savedInstanceState){
		// super

		PrefUtils.init(this);

		mLUtils = LUtils.getInstance(this);
	}

	private void trySetupSwipeRefresh() {}

	protected void setProgressBarTopWhenActionBarShown(int progressBarTopWhenActionBarShown) {}

	private void updateSwipeRefreshProgressBarTop() {}

	protected int getSelfNavDrawerItem() {}

	private void setupNavDrawer(){

		int selfItem = getSelfNavDrawerItem();

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (mDrawerLayout == null) {
			return;
		}

		mDrawerLayout.setStatusBarBackgroundColor{
			getResources().getColor(R.color.xxx);
			ScrimInsetsScrollView navDrawer = (ScrimInsetsScrollView)
			mDrawerLayout.findViewById(R.id.navdrawer);
		}

		mActionBarToolbar.setNavigationIcon(R.drawable.ic_drawer);
		mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mDrawerLayout.openDrawer(Gravity.START);
			}
		}); 

		mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
			@Override
			public void onDrawerClosed(View drawerView) {
                // run deferred action, if we have one
				if (mDeferredOnDrawerClosedRunnable != null) {
					mDeferredOnDrawerClosedRunnable.run();
					mDeferredOnDrawerClosedRunnable = null;
				}
				if (mAccountBoxExpanded) {
					mAccountBoxExpanded = false;
					setupAccountBoxToggle();
				}
				onNavDrawerStateChanged(false, false);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				onNavDrawerStateChanged(true, false);
			}

			@Override
			public void onDrawerStateChanged(int newState) {
				onNavDrawerStateChanged(isNavDrawerOpen(), newState != DrawerLayout.STATE_IDLE);
			}

			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				onNavDrawerSlide(slideOffset);
			}
		});
	}

	protected void onResume(){
		super.onResume();
	}

	private void onMainContentScrolled(int currentY, int deltaY){
		if(deltaY > mActionBarAutoHideSensivity){
			deltaY = mActionBarAutoHideSensivity;
		}else if(deltaY < -mActionBarAutoHideSensivity){
			deltaY = -mActionBarAutoHideSensivity;
		}

		if (Math.signum(deltaY) * Math.signum(mActionBarAutoHideSignal) < 0) {
            // deltaY is a motion opposite to the accumulated signal, so reset signal
			mActionBarAutoHideSignal = deltaY;
		} else {
            // add to accumulated signal
			mActionBarAutoHideSignal += deltaY;
		}

		boolean shouldShow = currentY < mActionBarAutoHideMinY ||
		(mActionBarAutoHideSignal <= -mActionBarAutoHideSensivity);

		autoShowOrHideActionBar(shouldShow);
	}
}

protected void enableActionBarAutoHide(final ListView listView){
	initActionBarAutoHide();
	listView.setOnScrollListener(new AbsListView.OnScrollListener(){
		//门槛
		final static int ITEMS_THRESHOLD = 3;
		int lastFvi = 0;

		public void onScrollStateChanged(AbsListView view, int scrollState){

		}

		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,int totalItemCount){
			onMainContentScrolled(firstVisibleItem <= ITEMS_THRESHOLD ? 0 : Integer.MAX_VALUE,
				lastFvi - firstVisibleItem >0 
				? Integer.MIN_VALUE
				: lastFvi == firstVisibleItem ? 0 : Integer.MAX_VALUE;

				);
			lastFvi = firstVisibleItem;
		}

	});
}

public class HelpUtils{

	public static void showAbout(Activity activity){
		FragmentManager fm = activity.getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment prev = fm.fnidFragmentByTag("dialog_about");
		if( pre != null){
			ft.remove(prev);
		}
		ft.addToBackStack(null);

		new AboutDialog().show(ft, "dialog_about");
	}

	public static class showOpenSourceLiceses(Activity activity){
		FragmentManager fm = activity.getFragmentManager();
		FragmentTransaction  ft = fm.beginTransaction();
		Fragment prev = fm.findFragmentByTag("xxx");
		if(prev != null){
			ft.remove(prev);
		}
		ft.addToBackStack(null);

		new OpenSourceLicensesDialog().show(ft, "xxx");
	}

	public static class AboutDialog extends DialogFragment{

		private static final String = "xxx";

		public AboutDialog(){

		}

		public Dialog onCreateDialog(Bundle savedInstanceState){
			Packagemanger pm = getActivity().getPackageManager();

			String packageName = getActivity().getPackageName();
			String versionName;

			try{
				PackageInfo info = pm.getPackageInfo(packageName, 0);
				versionName = info.versionName;
			}catch( PackageManager.NameNotFoundException e){
				versionName = VERSION_UNAVAILABLE;
			}

			SpannableStringBuilder aboutBody = new SpannableStringBuilder();
			aboutBody.append(Html.fromHtml(getString(R.String.about_body,versionName)));

			SpannableStringBuilder licenseLink = new SpannableStringBuilder("xxx");
			licenseLink.setSpan(new ClickableSpan(){
				public void onClick(View view){
					HelpUtils.showOpenSourceLiceses(getActivity());
				}
			}, 0, licenseLink.length(),0);
			aboutBody.append("\n\n");
			aboutBody.append(licensesLink);

			LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
			TextView aboutBodyView = (TextView) layoutInflater.inflate(R.layout.xxx,null);
			aboutBodyView.setText(aboutBody);
			aboutBodyView.setMovementMethod(new LinkMovementMethod());

			return new AlertDialog.Builder(getActivity())
			.setTitle("xxx")
			.setView(aboutBodyView)
			.setPositiveButton("OK",new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int whichButton){
					dialog.dismiss();
				}
			})
			.create();
		}
	}

	public static class OpenSourceLicensesDialog extends DialogFragment{

		public OpenSourceLicensesDialog(){

		}

		public Dialog onCreateDialog(Bundle savedInstanceState){
			WebView webView = new WebView( getActivity());
			webView.loadUrl("wwww.xxx");

			return new AlertDialog.Builder(getActivity())
			.setTitle("xxx")
			.setView(webView)
			.setPositiveButton("OK",
				new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int whichButton){
						dialog.dismiss();
					};
				})
			.create();
		}
	}

	public static class EulaDialog extends DialogFragment{

		public EulaDialog(){

		}

		public Dialog onCreateDialog(Bundle savedInstanceState){
			int padding = getResources().getDimensionPixelSize(R.dimen.xxx);

			TextView eulaTextView = new TextView(getActivity());
			eulaTextView.setText(Html.fromHtml("xxx"));
			eulaTextView.setMovementMethod(LinkMovementMethod.getInstance());
			eulaTextView.setPadding(padding,padding,padding,padding);

			return new AlertDialog.Builder(getActivity())
			.setTitle("xxx")
			.setView(eulaTextView)
			.setPositiveButton("OK",
				new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int whichButton){
						dialog.dismiss();
					};
				})
			.create();
		}
	}
}


public class MultiSwipeRefreshLayout extends SwipeRefreshLayout{
	private CanChildScrollUpCallback mCanChildScrollUpCallback;

	private Drawable mForegroundDrawable;

	public MultiSwipeRefreshLayout(Context context) {
		this(context, null);
	}

	public MultiSwipeRefreshLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		final TypedArray a = context.obtainStyledAttributes(attrs,
			R.styleable.MultiSwipeRefreshLayout, 0, 0);

		mForegroundDrawable = a.getDrawable(R.styleable.MultiSwipeRefreshLayout_foreground);
		if (mForegroundDrawable != null) {
			mForegroundDrawable.setCallback(this);
			setWillNotDraw(false);
		}

		a.recycle();
	}

	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if (mForegroundDrawable != null) {
			mForegroundDrawable.setBounds(0, 0, w, h);
		}
	}

	public void draw(Canvas canvas) {
		super.draw(canvas);
		if (mForegroundDrawable != null) {
			mForegroundDrawable.draw(canvas);
		}
	}

	public void setCanChildScrollUpCallback(CanChildScrollUpCallback canChildScrollUpCallback) {
		mCanChildScrollUpCallback = canChildScrollUpCallback;
	}

	public static interface CanChildScrollUpCallback {
		public boolean canSwipeRefreshChildScrollUp();
	}

	public boolean canChildScrollUp() {
		if (mCanChildScrollUpCallback != null) {
			return mCanChildScrollUpCallback.canSwipeRefreshChildScrollUp();
		}
		return super.canChildScrollUp();
	}

}

public class UIUtils {

	public static float getProgress(int value, int min, int max){
		if(min == max){
			throw new IllegaArgumentException("cannot equal");
		}

		return (value - min)/(float)(max - min);
	}
}


public class BrowseSessionsActivity extends BaseActivity implements SessionsFragment.Callbacks{

}

public class SessionDetailActivity extends BaseActivity implments
LoaderManager.LoaderCallbacks<Cursor>,
ObservableScrollView.Callbacks{

	private ViewTreeObserver.addOnGlobalLayoutListener mGlobalLayoutListener
	= new ViewTreeObserver.OnGlobalLayoutListener(){

		public void onGlobalLayout(){
			mAddScheduleButtonHeightPixels = mAddScheduleButton.getHeight();
			recomputePhotoAndScrollingMetrics();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		final Toolbar toolbar = getActionBarToolbar();
		toolbar.setNavigationIcon(shouldBeFloatingWindow
			? R.drawable.ic_ab_close : R.drawable.ic_up);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		// 最外面一层可滑动
		mScrollView = (ObservableScrollView) findViewById(R.id.scroll_view);
		mScrollView.addCallbacks(this);

		mHeaderBox = findViewById(R.id.header_session);
		mPhotoViewContainer = findViewById(R.id.session_photo_container);

		ViewTreeObserver vto = mScrollView.getViewTreeObserver();
		if (vto.isAlive()) {
			vto.addOnGlobalLayoutListener(mGlobalLayoutListener);
		}
	}


	public void onScrollChanged(int deltaX, int deltaY){

		int scrollY = mScrollView.getScrollY();

		float newTop = Math.max(mPhotoHeightPixels, scrollY);
		mHeaderBox.setTranslationY(newTop);

		float gapFillProgress = 1;
		if(mPhotoHeightPixels != 0){
			gapFillProgress = Math.min(
				Math.max(
					UIUtils.getProgress(scrollY,0,mPhotoHeightPixels)
					,0)
				,1);
		}

		mPhotoViewContainer.setTranslationY(scrollY * 0.5f);
	}



}

public class ObservableScrollView extends ScrollView{
	private ArrayList<Callbacks> mCallbacks = new Arraylist<Callbacks>();

	public ObservableScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		for (Callbacks c : mCallbacks) {
			c.onScrollChanged(l - oldl, t - oldt);
		}
	}

	public int computeVerticalScrollRange(){
		return super.computeVerticalScrollRange();
	}

	public void addCallbacks(Callbacks listener) {
		if (!mCallbacks.contains(listener)) {
			mCallbacks.add(listener);
		}
	}

	public static interface Callbacks {
		public void onScrollChanged(int deltaX, int deltaY);
	}

}

private void recomputePhotoAndScrollingMetrics(){

	mHeaderHeightPixels = mHeaderBox.getHeight();

	mPhotoHeightPixels = 0;

	ViewGroup.LayoutParams lp;
	lp = mPhotoViewContainer.getLayoutParams();
	if (lp.height != mPhotoHeightPixels) {
		lp.height = mPhotoHeightPixels;
		mPhotoViewContainer.setLayoutParams(lp);
	}

	ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams)mDetailsContainer.getLayoutParams();
	if (mlp.topMargin != mHeaderHeightPixels + mPhotoHeightPixels) {
		mlp.topMargin = mHeaderHeightPixels + mPhotoHeightPixels;
		mDetailsContainer.setLayoutParams(mlp);
	}

	onScrollChanged(0, 0); // trigger scroll handling    

}

// popmenu

public class FeedContextMenu extends LinearLayout{

	private static final int CONTEXT_MENU_WIDTH = Utils.dpToPx(240);

	private int feedItem = -1;

	private OnFeedContextMenuItemClickListener onItemClickListener;

	public FeedContextMenu(Context context){
		super(context);
		init();
	}

	private void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.view_context_menu,this,true);
		setBackgroundResource(R.drawable.bg_container_shadow);
		setOrientation(VERTICAL);
		setLayoutParams(new LayoutParams(CONTEXT_MENU_WIDTH,ViewGroup.LayoutParams.WRAP_CONTENT));
	}

	public void bindToItem(int feedItem){
		this.feedItem = feedItem;
	}

	protected void onAttachedToWindow(){
		super.onAttacchedToWindow();
		ButterKnife.inject(this);
	}

	public void dismiss(){
		((ViewGroup) getParent()).removeView(FeedContextMenu.this);
	}

	@OnClick(R.id.btnReport)
	public void onReportClick(){
		if( onItemClickListener != null){
			onItemClickListener.onReportClick(feedItem);
		}
	}

	@OnClick(R.id.btnSharePhoto)
	public void onSharePhotoClick(){
		if(onItemClickListener != null){
			onItemClickListener.onSharePhotoClick(feedItem);
		}
	}

	@OnClick(R.id.btnCopyShareUrl)
	public void onCopyShareUrlClick(){
		if(onItemClickListener != null){
			onItemClickListener.onCopyShareUrlClick(feedItem);
		}
	}

	@OnClick(R.id.btnCancel)
	public void onCancelClick() {
		if (onItemClickListener != null) {
			onItemClickListener.onCancelClick(feedItem);
		}
	}

	public void setOnFeedMenuItemClickListener(OnFeedContextMenuItemClickListener onItemClickListener){
		this.onItemClickListener = onItemClickListener;
	}

	public interface OnFeedContextMenuItemClickListener{

		public void onReportClick(int feedItem);

		public void onSharePhotoClick(int feedItem);

		public void onCopyShareUrlClick(int feedItem);

		public void onCancelClick(int feedItem);
	}
}


// menu selector
// res/drawable/btn_context_menu.xml
<selector xmlns:android="http://schemas.android.com/apk/res/android">
<item android:drawable="@color/btn_context_menu_normal" android:state_focused="false" android:state_pressed="false">
<item android:drawable="@color/btn_context_menu_pressed" android:state_pressed="true">
<item android:drawable="@color/btn_context_menu_pressed" android:state_focused="true">
</selector>	
// res/drawable-v21/btn_context_menu.xml
<ripple xmlns:android="http://schemas.android.com/apk/res/android"
android:color="@color/btn_context_menu_pressed">
<item>
<shape android:shape="rectangle">
<solid android:color="@color/btn_context_menu_normal" />
</shape>
</item>
</ripple>


public class FeedContextMenuManager extends RecyclerView.OnScrollListener implements View.OnAttachStateChangeListener{

	private static FeedContextMenuManager instance;

	private FeedContextMenu contextMenuView;

	public static FeedContextMenuManager getInstance(){
		if(instance == null){
			instance = new FeedContextMenuManager();
		}
		return instance;
	}

	public void onViewAttachedToWindow(View v){

	}

	public void onViewDetachedFromWindow(View v){
		contextMenuView = null;
	}

	public void toggleContextMenuFromView(View openingView, int feedItem, FeedContextMenu.OnFeedContextMenuItemClickListener listener){
		if(contextMenuView == null){
			showContextMenuFromView(openingView,feedItem,listener);
		}else{
			hideContextMenu();
		}
	}

	private void showContextMenuFromView(final View openingView, int feedItem, FeedContextMenu.OnFeedContextMenuItemClickListener listener){
		if( !isContextMenuShowing){
			isContextMenuShowing = true;
			contextMenuView = new FeedContextMenu(openingView,getContext());
			contextMenuView.bindToItem(feedItem);
			contextMenuView.addOnAttachStateChangeListener(this);
			contextMenuView.setOnFeedMenuItemClickListener(listener);

			((ViewGroup)openingView.getRootView().findViewById(android.R.id.content)).addView(contextMenuView);

			contextMenuView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.addOnPreDrawListener(){

				public boolean onPreDraw(){
					contextMenuView.getViewTreeObserver().removeOnPreDrawListener(this);
					setupContextMenuInitialPosition(openingView);
					performShowAnimation();
					return false;
				}

			});
		}
	}

	private void setupContextMenuInitialPosition(View openingView){
		final int[] openingViewLocation = new int[2];
		openingView.getLocationOnScreen(openingViewLocation);
		int additionlBottomMargin = Utils.dpToPx(16);
		contextMenuView.setTranslationX(openingViewLocation[0] - contextMenuView.getWidth()/3);
		contextMenuView.setTranslationY(openingViewLocation[1] - contextMenuView.getHeight() - additionlBottomMargin);
	}

	private void performShowAnimation(){
		contextMenuView.setPivotX(contextMenuView.getWidth()/2);
		contextMenuView.setPivotY(contextMenuView.getHeight());
		contextMenuView.setScaleX(0.1f);
		contextMenuView.setScaleY(0.1f);
		contextMenuView.animate()
		.scaleX(1f).scaleY(1f)
		.setDuration(150)
		.setInterpolator(new Overshootinterpolator(){
			public void onAnimationEnd(){
				isContextMenuShowing = false;
			}
		});
	}

	public void hideContextMenu(){
		if( !isContextMenuShowing){
			isContextMenuShowing = true;
			performDismissAnimation();
		}
	}

	private void performDismissAnimation(){
		contextMenuView.setPivotX(contextMenuView.getWidth()/2);
		contextMenuView.setPivotY(contextMenuView.getHeight());
		contextMenuView.animate()
		.scaleX(0.1f).scaleY(0.1f)
		.setDuration(150)
		.setInterpolator(new AccelerateInterpolatro())
		.setStartDelay(100)
		.setListener(new AnimatorListenerAdater(){
			public void onAnimationEnd(Animator animationi){
				if( contextMenuView != null){
					contextMenuView.dismiss();
				}
				isContextMenuShowing = false;
			}
		})
	}

	public void onScrolled(RecyclerView recyclerView, int dx, int dy){
		if(contextMenuView != null){
			hideContextMenu();
			contextMenuView.setTranslationY(contextMenuView.getTranslationY() - dy);
		}
	}
}

<TextSwitcher
android:id="@+id/tsLikesCounter"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:layout_marginLeft="8dp"
android:layout_marginRight="8dp"
android:inAnimation="@anim/slide_in_likes_counter"
android:outAnimation="@anim/slide_out_likes_counter">

<TextView 
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text="123 likes"
android:textColor="@color/text_like_counter" />

<TextView
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:textColor="@color/text_like_counter" />
</TextSwitcher>

private void updateLikesCounter(CellFeedViewHolder holder, boolean animated){
	int currentLikesCount = likesCount.get(holder.getPosition()) + 1;
	String likesCountText = context.getResources().getQuantityString(R.plurals.likes_count,currentLikesCount,currentLikesCount);

	if(animated){
		holder.tsLikesCounter.setText(likesCountText);
	}else{
		holder.tsLikesCounter.setCurrentText(likesCountText);
	}

	likesCount.put(holder.getPosition(),currentLikesCount);
}

<String name="welcomme">Hello,%1$s! You have %2$d new messages</string>
Resources res = getresources();
String text = String.format(res.getString(R.string.xxx), username, mailCount);


private void updateHeartButton(final CellFeedViewHolder holder, boolean animated){
	if( animated){
		if( !likeAnimatioins.containsKey(holder)){
			AnimatorSet animatorSet = new AnimatorSet();
			likeAnimatioins.put(holder, animatorSet);

			ObjectAnimator ratationAnim = ObjectAnimator.ofFloat(holder.btnLike, "rotationi", 0f, 360f);
			rotationAnim.setDuration(300);
			rotationAnim.setInterpolator(ACCELERATE_INTERPOLATOR);

			ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(holder.btnLike, "scaleX", 0.2f, 1f);
			bounceAnimX.setDuration(300);
			bounceAnimX.setInterpolator(OVERSHOOT_INTEPOLATOR);

			ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(holder.btnLike,"scaleY", 0.2f, 1f);
			bounceAnimY.setDuration(300);
			bounceAnimY.setInterpolator(OVERSHOOT_INTEPOLATOR);
			bounceAnimY.addListener(new AnimatorListenerAdater(){
				public void onAnimationStart(Animator animation){
					holder.btnLike.setImageResource(R.drawable.ic_heart_red);
				}
			});

			animatorSet.play(rotationAnim);
			animatorSet.play(bounceAnimX).with(bounceAnimY).after(rotationAnim);

			animatorSet.addListener(new AnimatiorListenerAdapter(){

				public void onAnimationEnd(Animator animation){
					resetLikeAnimationState(holder);
				}
			});

			animatorSet.start();
		}
	}else{

		if (likedPositions.contains(holder.getPosition())) {
			holder.btnLike.setImageResource(R.drawable.ic_heart_red);
		} else {
			holder.btnLike.setImageResource(R.drawable.ic_heart_outline_grey);
		}
	}
}

// Vector
<?xml version="1.0" encode="UTF-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
android:viewportWidth="500"
android:viewportHeight="500"
android:width="500px"
android:height="500px">
<group android:name="android">
<path
android:name="head"
android:fillColor="#9fbf3b"
android:pathData="M301.314,xxxxxxxxxxxxxxx" />
<path
android:name="left_eye"
android:fillColor="#FFFFFF"
android:pathData="M203.956,xxxxxxxxxxxxxxx" />
</vector>

<group android:name="android">
<group android:name="head_eyes">
<path
android:name="head"
android:fillColor="#xxx"
android:pathData="Mxxxxxxx" />
<path
android:name="left_eye"
android:fillColor="#xxx"
android:pathData="Mxxxxxxx" />
<path
android:name="right_eye"
android:fillColor="#xxx"
android:pathData="Mxxxxxxx" />		
</group>
<group android:name="arms">
<path
android:name="left_arm"
android:fillColor="#xxx"
android:pathData="Mxxxxxxx" />
<path
android:name="right_arm"
android:fillColor="#xxx"
android:pathData="Mxxxxxxx" />		
</group>
</group>

<?xml version="1.0" encoding="UTF-8"?>
<animated-vector xmlns:android="http://schemas.android.com/apk/res/android"
android:drawable="@drawable/android">

<target
android:animation="@animator/shrug"
android:name="head_eyes" />

<target
android:animation="@animator/shurg"
android:name="arms" />

</animated-vector>

// res/animator/shrug.xml
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android">
<ObjectAnimator
android:propertyName="translateY"
android:valueType="floatType"
android:valueFrom="0"
android:valueTo="-10"
android:repeatMode="reverse"
android:repeatCount="infinite"
android:duratioin="250" />
</set>

ImageView androidImageView = (ImageView)findViewById(R.id.xxx);
Drawable drawable = androidImageView.getDrawable();
if( drawable instanceof Animatable){
	((Animatable) drawable).start();
}

// 动画
ObjectAnimator
.ofFloat(view, "rotationX", 0.0F, 360.0F)
.setDuration(1000)
.start();

animator.addUpdateListener(new AnimatorUpdateListener(){
	public void onAnimationUpdate(ValueAnimator arg0){

	}
});

ObjectAnimator anim = ObjectAnimator
.ofFloat(view, "xxx", 1.0F, 0.0F)
.setDuration(500);
anim.start();

anim.addUpdateListener(new AnimatorUpdateListener(){
	public void onAnimationUpdate(ValueAnimator animation){
		floatcVal = (Float) animation.getAnimatedValue();
		view.setAlpha(cVal);
		view.setScaleX(cVal);
		view.setScaleY(cVal);
	}
});

private static class WrapperView{
	private View mTarget;

	public WrapperView(View target){
		mTarget = target;
	}

	public int getWidth(){
		return mTarget.getLayoutParams().width;
	}

	public void setWidth(int width){
		mTarget.getLayoutParams().width = width;
		mTarget.requestLayout();
	}
}

ViewWrapper wrapper = new ViewWrapper(mButton);
ObjectAnimator.ofInt(wrapper,"width",500).setDuration(5000).start();

// 多动画效果的另一种实现方式
public void propertyValuesHolder(View view){
	PropertyValueHolder pvhX = PropertyValueHolder.ofFloat("alpha", 1f, 0f, 1f);
	PropertyValueHolder pvhY = PropertyValueHolder.ofFloat("scaleX",1f, 0, 1f);
	PropertyValueHolder pvhZ = PropertyValueHolder.ofFloat("scaleY",1f, 0, 1f);

	ObjectAnimator.ofPropertyValueHodler(view, pvhX, pvhY, pvhZ)
	.setDuration(1000).start();
}

// 动画更新的过程中，会不断调用setPropName更新元素属性，所有使用objectAnimator更新某个属性，必须有getter和setter
// 使用valueAnimator 不需要getter 和 setter
public void verticalRun(View view){
	ValueAnimator animator = ValueAnimator.ofFloat(0, mScreenHeight - mBuleBall.getHeight());
	animator.setTarget(mBuleBall);
	animator.setDuration(1000).start();
	animator.addUpdateListener(new AnimatorUpdateListener(){
		public void onAnimationiUpdate(ValueAnimator animation){
			mBuleBall.setTranslationY((Float)animation.getAnimatedValue());
		}
	});
}

public void parabola(View view){
	ValueAnimator valueAnimator = new ValueAnimator();
	valueAnimator.setDuration(3000);
	valueAnimator.setObjectValues(new PointF(0,0));
	valueAnimator.setInterpolator(new LinearInterpolator());
	valueAnimator.setEvaluator(new TypeEvaluator<PointF>(){
		public PointF evaluate(float fraction, PointF startValue, PointF endValue){
			PointF point = new PointF();
			point.x = xxx;
			point.y = xxx;
			return point;
		}
	});

	valueAnimator.start();
	valueAnimator.addUpdateListener(new AnimatiorUpdateListener(){

		public void onAnimationUpdate(ValueAnimator animation){
			PointF point = (PointF) animation.getAnimatedValue();
			mBuleBall.setX(point.x);
			mBuleBall.setY(point.y);
		}
	});
}

//可以看到，因为ofInt,ofFloat等无法使用，我们自定义了一个TypeValue，每次根据当前时间返回一个PointF对象，
//（PointF和Point的区别就是x,y的单位一个是float,一个是int;RectF,Rect也是）

public void fadeOut(View view){
	ObjectAnimator anim = ObjectAnimator.ofFloat(mBuleBall, "alpha", 0.5f);
	anim.addListener(new AnimatorListener(){

		public void onAnimationStart(Animator animation){

		}

		public void onAnimationRepeat(Animator animation){

		}

		public void onAnimationEnd(Animator animation){

			ViewGroup parent = (ViewGroup)mBuleBall.getParent();
			if(parent != null){
				parent.removeView(mBuleBall);
			}
		}

		public void onAnimationCancel(Animator animation){

		}
	});
	anim.start();
}

// AnimatorListenerAdapter继承了AnimatorListener接口，然后空实现了所有的方法~
// animator还有cancel()和end()方法：cancel动画立即停止，停在当前的位置；end动画直接到最终状态

public void togetherRun(View view){
	ObjectAnimator anim1 = ObjectAnimator.ofFloat(mBlueBall, "scaleX", 1.0f, 2f);
	ObjectAnimator anim2 = ObjectAnimator.ofFloat(mBlueBall, "scaleY", 1.0f, 2f);

	AnimatorSet animSet = new AnimatorSet();
	animSet.setDuration(2000);
	animSet.setInterpolator(new LinearInterpolator());
	animSet.playTogether(anim1,anim2);
	animSet.start();
}

public void playWithAfter(View view){
	float cx = mBlueBall.getX();

	ObjectAnimator anim1 = xxx;
	ObjectAnimator anim2 = xxx;
	ObjectAnimator anim3 = xxx;
	ObjectAnimator anim4 = xxx;

	AnimatorSet animSet = new AnimatorSet();
	animSet.play(anim1).with(anim2);
	animSet.play(anim2).with(anim3);
	animSet.play(anim4).with(anim3);
	animSet.setDuration(10000);
	animSet.start();
}

// 主题更换
// attrs.xml
<?xml version="1.0" encoding="utf-8"?>  
<resources>  
<attr name="colorValue" format="color" />  
<attr name="floatValue" format="float" />  
<attr name="integerValue" format="integer" />  
<attr name="booleanValue" format="boolean" />  
<attr name="dimensionValue" format="dimension" />  
<attr name="stringValue" format="string" />  
<attr name="referenceValue" format="reference" />  
</resources>  

<style name="SwitchTheme1" parent="@android:style/Theme.Black">  
<item name="colorValue">#FF00FF00</item>  
<item name="floatValue">0.35</item>  
<item name="integerValue">33</item>  
<item name="booleanValue">true</item>  
<item name="dimensionValue">76dp</item>  
<!-- 如果string类型不是填的引用而是直接放一个字符串,在布局文件中使用正常,但代码里获取的就有问题 -->  
<item name="stringValue">@string/hello_world</item>  
<item name="referenceValue">@drawable/hand</item>  
</style>  

<style name="SwitchTheme2" parent="@android:style/Theme.Wallpaper">  
<item name="colorValue">#FFFFFF00</item>  
<item name="floatValue">1.44</item>  
<item name="integerValue">55</item>  
<item name="booleanValue">false</item>  
<item name="dimensionValue">76px</item>  
<item name="stringValue">@string/action_settings</item>  
<item name="referenceValue">@drawable/ic_launcher</item>  
</style>  

<TextView  
android:id="@+id/themeColor"  
android:layout_width="wrap_content"  
android:layout_height="wrap_content"  
android:layout_alignLeft="@+id/themeText"  
android:layout_below="@+id/themeText"  
android:text="TextView"  
android:textColor="?attr/colorValue" />  


if (useThemeBlack)  
	setTheme(R.style.SwitchTheme1);  
else  
	setTheme(R.style.SwitchTheme2);  
setContentView(R.layout.activity_theme_switch);

<?xml version="1.0" encoding="utf-8"?>  
<objectAnimator xmlns:android="http://schemas.android.com/apk/res/android"  
android:duration="10000"
android:propertyName="scaleX"
android:valueFrom="1.0"
android:valueTo="2.0"
android:valueType="floatType" >
</objectAnimator>

public void scaleX(View view){
	Animatior anim = AnimatorInflater.loadAnimator(this, R.animator.scalex);
	anim.setTarget(view);
	anim.start();
}

<?xml version="1.0" encoding="utf-8"?>  
<set xmlns:android="http://schemas.android.com/apk/res/android"  
android:ordering="together" >  

<objectAnimator  
android:duration="1000"  
android:propertyName="scaleX"  
android:valueFrom="1"  
android:valueTo="0.5" >  
</objectAnimator>  
<objectAnimator  
android:duration="1000"  
android:propertyName="scaleY"  
android:valueFrom="1"  
android:valueTo="0.5" >  
</objectAnimator>  

</set> 

Animator anim = AnimtorInflater.loadAnimator(this, R.animator.scale);
mMv.setPivotX(0);
mMv.setPivotY(0);
mMv.invalidate();
anim.setTarget(mMv);
anim.start();

// 布局动画
LayoutTransition transition = new LayoutTransition();
transition.setAnimator(LayoutTransition.CHANGE_APPEARING, transition.getAnimator(LayoutTransition.CHANGE_APPEARING));
transition.setAnimator(LayoutTransition.APPEARING,null);
transition.setAnimator(LayoutTransition.DISAPPEARING,null);
transition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING,null);
mGridLayout.setLayoutTransitioni(transition);

//LayoutTransition.APPEARING 当一个View在ViewGroup中出现时，对此View设置的动画

//LayoutTransition.CHANGE_APPEARING 当一个View在ViewGroup中出现时，对此View对其他View位置造成影响，对其他View设置的动画

//LayoutTransition.DISAPPEARING  当一个View在ViewGroup中消失时，对此View设置的动画

//LayoutTransition.CHANGE_DISAPPEARING 当一个View在ViewGroup中消失时，对此View对其他View位置造成影响，对其他View设置的动画

//LayoutTransition.CHANGE 不是由于View出现或消失造成对其他View位置造成影响，然后对其他View设置的动画。


private class DragController implements RecyclerView.OnItemTouchListener{

	public static final int ANIMATION_DURATION = 100;
	private RecyclerView recyclerView;
	private ImageView overlay;
	private final GestureDetectorCompt gestureDetector;

	private boolean isDragging = false;
	private View draggingView;
	private boolean isFirst = true;
	private long draggingId = -1;
	private float startY = 0f;
	private Rect startBounds = null;

	public DragController(RecyclerView recyclerView, ImageView overlay){
		this.recyclerView = recyclerView;
		this.overlay = overlay;

		GestureDetector.SimpleOnGestureListener longClickGestureListener = 
		new GestureDetector.SimpleOnGestureListener(){
			public void onLongPress(MotionEvent e){
				super.onLongPress(e);

				isDragging = true;
				dragStart(e.getX(), e.getY());
			}
		}
		this.gestureDetector = new GestureDetectorCompat(recyclerView.getContext(), longClickGestureListener);
	}

	public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e){
		if(isDragging){
			return true;
		}
		gestureDetector.onTouchEvent(e);
		return false;
	}

	public boolean onTouchEvent(RecyclerView rv, MotionEvent e){
		int y = (int) e.getY();
		if(e.getAction() == MotionEvent.ACTION_UP){
			dragEnd();
			isDragging = false;
		}else{
			drag(y);
		}
	}

	private void dragStart(float x, float y){
		draggingView = recyclerView.findChildViewUnder(x,y);
		View first = recyclerView.getChildAt(0);
		isFirst = draggingView == first;
		startY = y - draggingView.getTop();
		paintViewToOverlay(draggingView);
		overlay.setTranslationY(y - startY);

		draggingView.setVisibility(View.INVISIBLE);
		draggingId = recyclerView.getChildItemId(draggingView);
		startBounds = new Rect(draggingView.getLeft(),draggingView.getTop(),draggingView.getRight(),draggingView.getBottom());
	}

	private void drag(int y){
		overlay.setTranslationY(y - startY);
		if(!isInPreviousBounds()){
			View view = recyclerView.findChildViewUnder(0,y);
			if( recyclerView.getChildPosition(view) != 0 && view != null){
				swapViews(view);
			}
		}
	}

	private void swapViews(View current){
		long replacementId = recyclerView.getChildItem(current);
		FeedAdapter adapter = (FeedAdapter) recyclerView.getAdapter();

		int start = adapter.getPositionForId(replacementId);
		int end = adapter.getPositionForId(draggingId);

		adapter.moveItem(start, end);
		if(isFirst){
			recyclerView.scrollToPosition(end);
			isFirst = false;
		}
		startBounds.top = current.getTop();
		startBounds.bottom = current.getBottom();
	}

	private void dragEnd(){
		overlay.setImageBitmap(null);
		draggingView.setVisibility(View.VISIBLE);
		float translationY = overlay.getTranslationY();
		draggingView.setTranslationY(translationY - startBounds.top);
		draggingView.animate().translationY(0f).setDuration(ANIMATION_DURATION).start();
	}

	private void paintViewToOverlay(View view){
		Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		view.draw(canvas);
		overlay.setImageBitmap(bitmap);
		overlay.setTop(0);
	}

	public boolean isInPreviousBounds(){
		float overlayTop = overlay.getTop() + overlay.getTranslationY();
		float overlayBottom = overlay.getBottom() + overlay.getTranslationY();
		return overlayTop < startBounds.bottom && overlayBottom > startBounds.top;
	}
}

// 官方 recyclerview
private enum LayoutManagerType{
	GRID_LAYOUT_MANAGER,
	LINEAR_LAYOUT_MANAGER
}

protected LayoutManagerType mCurrentLayoutManagerType;

public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
	View rootView = inflater.inflate(R.layout.xxx, container, null);

	rootView.setTag(TAG);

	mRecyclerView = (RecyclerView) rootView.findViewById(R.id.xxx);
	mLayoutManger = new LinearLayoutManger(getActivity());

	mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

	if(savedinstanceState != null){
		mCurrentLayoutManagerType  = (LayoutManagerType) savedInstanceState.getSerializable(KEY_LAYOUT_MANAGER);
	}
	setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

	mAdpater = new CustomAdapter( mDataset);
	mRecyclerView.setAdapter(mAdpater);

}

public void setRecyclerViewLayoutManager(LayoutManagerType layoutMagerType){
	int scrollPosition = 0;

	if(mRecyclerView.getLayoutManager() != null){
		scrollPosition = ((LinearLayoutManger) mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
	}

	switch(layoutMagerType){
		case GRID_LAYOUT_MANAGER:
		mLayoutManger = new GridLayoutManger(getActivity(), SPAN_COUNT);
		mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
		break;
		case LINEAR_LAYOUT_MANAGER:
		mLayoutManger = new LinearLayoutManger(getActivity());
		mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
		break;
		default:
		mLayoutManger = new LinearLayoutManger(getActivity());
		mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
	}

	mRecyclerView.setLayoutManager(mLayoutManger);
	mRecyclerView.scrollToPosition(scrollPosition);
}

public void onSaveInstanceState(Bundle savedInstanceState){
	savedinstanceState.putSerializable(KEY_LAYOUT_MANAGER,mCurrentLayoutManagerType);
	super.onSaveInstanceState(savedInstanceState);
}

public class CustomAdapter extends RecyclerView.Adapter<CustomAdatper.ViewHolder>{

	private static final String TAG = "xxx";
	private String[] mDataSet;

	public static class ViewHolder extends RecyclerView.ViewHolder{
		private final TextView textView;

		public ViewHolder(View v){
			super(v);

			v.setOnclickListener(new View.OnClickListener(){
				public void onClick(View v){
					// ...
				}
			});

			textView = (TextView) v.findViewById(R.id.textView);
		}

		public TextView getTextView(){
			return textView;
		}
	}

	public CustomAdapter(String[] dataSet){
		mDataSet = dataSet;
	}

	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.xxx,viewGroup, null);
		return new ViewHolder(v);
	}

	public void onBindViewHolder(ViewHolder viewHolder, final int position){
		viewHolder.getTextView().setText(mDataSet[position]);
	}

	public int getItemCount(){
		return mDataSet.length;
	}
}

// dp px 画图
float textSize = getResources().getDimensionPixelSize(R.dimen.xxx);

float maxTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,maxTextSize, getResources().getDisplayMetrics());

setTextSize(TypedValue.COMPLEX_UNIT_PX, letterSize);

public class RevealBackgroundView extends View{
	public static final int STATE_NOT_STARTED = 0;
	public static final int STATE_FILL_STARTED = 1;
	public static final int STATE_FINISH = 2;

	private static final Interpolator INTERPOLATOR = new AccelerateInterpolator();
	private static final int FILL_TIME = 400;

	private int state = STATE_NOT_STARTED;

	private Paint fillPaint;
	private int currentRadius;
	ObjectAnimator revealAnimator;

	private int statrLocationX;
	private int startLocationY;

	private OnStateChangeListener onStateChangeListener;

	public RevealBackgroundView(Context context){
		super(context);
		init();
	}

	public RevealBackgroundView(Context context, AttributeSet attrs){
		suepr(context, attrs);
		init();
	}

	public RevealBackgroundView(Context context, AttributeSet attrs, int defStyleAttr){
		super(context, attrs, defStyle);
		init();
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public RevealBackgroundView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
		super(context, attrs, defStyleAttr, defStyleRes);
		init();
	}

	private void init(){
		fillPaint = new Paint();
		fillPaint.setStyle(Paint.Style.FILL);
		fillPaint.setColor(Color.WHITE);
	}

	public void startFromLocation(int[] tapLocationOnScreen){
		changeState(STATE_FILL_STARTED);
		statrLocationX = tapLocationOnScreen[0];
		startLocationY = tapLocationOnScreen[1];
		revealAnimator = ObjectAnimator.ofInt(this,"currentRadius",0,getWidth() + getHeight()).setDuration(FILL_TIME);
		revealAnimator.setInterpolator(INTERPOLATOR);
		revealAnimator.addListener(new AnimatorListenerAdapter(){

			public void onAnimationEnd(Animator animation){
				changeState(STATE_FINISH);
			}
		});
		revealAnimator.start();
	}

	public void setToFinishedFrame(){
		changeState(STATE_FINISH);
		invalidate();
	}

	protected void onDraw(Canvas canvas){
		if(state == STATE_FINISH){
			canvas.drawRect(0,0,getWidth(),getHeight(),fillPaint);
		}else{
			canvas.drawCircle(statrLocationX,startLocationY,currentRadius,fillPaint);
		}
	}

	private void onDraw(Canvas canvas){
		if(state == STATE_FINISH){
			canvas.drawRect(0,0,getWidth(),getHeight(), fillPaint);
		}else{
			canvas.drawCircle(statrLocationX,startLocationY, currentRadius, fillPaint);
		}
	}

	private void changeState(int state){
		if( this.state == state){
			return;
		}

		this.state = state;
		if(onStateChangeListener != null){
			onStateChangeListener.onStateChange(state);
		}
	}

	public void setOnStateChangeListener(OnStateChangeListener onStateChangeListener){
		this.onStateChangeListener = onStateChangeListener;
	}

	public void setCurrentRadius(int radius){
		this.currentRadius = radius;
		invalidate();
	}

	public static interface OnStateChangeListener{
		void onStateChange(int state);
	}
}

public viod onProfileClick(View v){
	int[] startingLocation = new int[2];
	v.getLocationOnScreen(startingLocation);
	startingLocation[0] += v.getWidth()/2;
	UserProfileActivity.startUserProfileFromLocation(startingLocation, this);
	overridePendingTransition(0,0);
}

public class UserProfileActivity extends BaseActivity implements RevvealBackgroundView.OnStateChangeListener{
	public static final String ARG_REVEAL_STATE_LOCATION = "reveal_start_location";

	private UserProfileAdapter userPhotosAdapter;

	public static void startUserProfileFromLocation(int[] startLocation, Activity startingActivity){
		Intent intent = new Intent(startingActivity, UserProfileActivity.class);
		intent.putExtra(ARG_REVEAL_STATE_LOCATION, startingLocation);
		startingActivity.startActivity(intent);
	}

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedinstanceState);
		setContentView(R.layout.xxx);
		setupUserProfileGrid();
		setupRevealBackground(savedinstanceState);
	}

	private void setupRevealBackground(Bundle savedinstanceState){
		vRevealBackground.setOnStateChangeListener(this);
		if(savedinstanceState == null){
			final int[] startingLocation = getIntent().getIntArrayExtra(ARG_REVEAL_STATE_LOCATION);
			vRevealBackground.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener(){
				public boolean onPreDraw(){
					vRevealBackground.getViewTreeObserver().removeOnPreDrawListener(this);
					vRevealBackground.startFromLocation(startingLocation);
					return false;
				}
			});
		}else{
			userPhotosAdapter.setLockedAnimationis(true);
			vRevealBackground.setToFinishedFrame();
		}
	}

	private void setupUserProfileGrid() {
		final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
		rvUserProfile.setLayoutManager(layoutManager);
		rvUserProfile.setOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				userPhotosAdapter.setLockedAnimations(true);
			}
		});
	}

	@Override
	public void onStateChange(int state) {
		if (RevealBackgroundView.STATE_FINISHED == state) {
			rvUserProfile.setVisibility(View.VISIBLE);
			userPhotosAdapter = new UserProfileAdapter(this);
			rvUserProfile.setAdapter(userPhotosAdapter);
		} else {
			rvUserProfile.setVisibility(View.INVISIBLE);
		}
	}
}

//SlidingPaneLayout 侧边栏
spl = (SlidingPaneLayout) this.findViewById(R.id.slidingpanellayout);
spl.setPanelSlideListener(new PanelSlideListener() {
	@Override
	public void onPanelClosed(View view) {
		MainActivity.this.getFragmentManager()
		.findFragmentById(R.id.leftfragment)
		.setHasOptionsMenu(false);
	}

	@Override
	public void onPanelOpened(View viw) {
		MainActivity.this.getFragmentManager()
		.findFragmentById(R.id.leftfragment)
		.setHasOptionsMenu(true);
	}

	@Override
	public void onPanelSlide(View arg0, float arg1) {

	}
});

// ViewDragHelper
// ViewDragHelper.Callback是连接ViewDragHelper与view之间的桥梁（这个view一般是指拥子view的容器即parentView）
// ViewDragHelper的实例是通过静态工厂方法创建的；
// 你能够指定拖动的方向；
// ViewDragHelper可以检测到是否触及到边缘；
// ViewDragHelper并不是直接作用于要被拖动的View，而是使其控制的视图容器中的子View可以被拖动，
// 如果要指定某个子view的行为，需要在Callback中想办法；

// ViewDragHelper类的设计决定了其适用于被包含在一个自定义ViewGroup之中，而不是对任意一个布局上的视图容器使用ViewDragHelper

public class DragLayout extends LinearLayout{
	private final ViewDragHelper mDragHelper;
	private View mDragView;

	public DragLayout(Context context){
		this(context, null);
	}

	public DragLayout(Context context, AttributeSet attrs){
		this(context, attrs, 0);
	}

	public DragLayout(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		mDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
	}

	public boolean onInterceptTouchEvent(MotionEvent ev){
		final int action = MotionEventCompat.getActionMasked(ev);
		if(action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP){
			mDragHelper.cancel();
			return false;
		}
		return mDragHelper.shouldInterceptTouchEvent(ev);
	}

	public boolean onTouchEvent(MotionEvent ev){
		mDragHelper.processTouchEvent(ev);
		return true;
	}

	public int clampViewPositionHorizontal(View child, int left, int dx){
		final int leftBound = getPaddingLeft();
		final int rightBound = getWidth() - mDragView.getWidth();
		final int newLeft = Math.min(Math.max(left,leftBound), rightBound);
		return newLeft;
	}

	@Override
	public int clampViewPositionVertical(View child, int top, int dy) {
		final int topBound = getPaddingTop();
		final int bottomBound = getHeight() - mDragView.getHeight();
		final int newTop = Math.min(Math.max(top, topBound), bottomBound);
		return newTop;
	}

//返回值可以决定一个parentview中哪个子view可以拖动
	@Override
	public boolean tryCaptureView(View child, int pointerId) {
		return child == mDragView1;
	}

	mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);

	public void onEdgeTouched(int edgeFlags, int pointerId){
		super.onEdgeTouched(edgeFlags,pointerId);
	}

	public void onEdgeDragStarted(int edgeFlags, int pointerId){
		mDragHelper.captureChildView(mDragView2, pointerId);
	}
}

// ValueAnimator
public class SampleAnimatorA extends Activity implements ValueAnimator.AnimatorUpdateListener{
	private ValueAnimator mAnim;

	protected void onCreate(Bundle savedInstanceState){
		mAnim = ValueAnimator.ofFloat(0.0f, 1.0f);
		mAnim.setDuration(2000);
		mAnim.addUpdateListener(this);
		mAnimLogButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				mAnim.start();
			}
		});
	}

	public void onAnimatoinUpdate(ValueAnimator animation){
		animation.getAnimatedValue();
	}
}

PropertyValuesHolder colorHolder = PropertyValuesHolder.ofObject("color",new ArgbEvaluator(),Integer.valueOf(xxx),Integer.valueOf(xxx));
mColorAnim2 = ObjectAnimator.ofPropertyValueHodler(xxx,colorHolder);
mColorAnim2.start();

// 侧滑栏 硬编码进activity
public class GlobalMenuView extends ListView implments View.OnClickListener{

	private OnHeaderClickListener onHeaderClickListener;
	private GlobalMenuAdapter globalMenuAdapter;
	private ImageView ivUserProfilePhoto;
	private int avatarSize;
	private String profilePhoto;

	public GlobalMenuView(Context context){
		super(context);
		inti();
	}

	private void init(){
		setChoiceMode(CHOICE_MODE_SINGLE);
		setDivider(getResources().getDrawable(android.R.color.transparent));
		setDividerHight(0);
		setBackgroundColor(Color.WHITE);

		setupHeader();
		setupAdapter();
	}

	private void setupAdapter(){
		globalMenuAdapter = new GlobalMenuAdapter(getContext());
		setAdapter(globalMenuAdapter);
	}

	private void setupHeader(){
		this.avatarSize = getResources().getDimensionPixelSize(R.dimen.xxx);
		this.profilePhoto = getResources().getString(R.string.xxx);

		setHeaderDividerEnabled(true);
		View vHeader = LayoutInflater.from(getContext()).inflate(R.layout.xxx);
		ivUser.ProfilePhoto = (ImageView)vHeader.findViewById(R.id.xxx);
		Picasso.with(getContext())
		.load(profilePhoto)
		.placeholder(R.drawable.xxx)
		.resize(avatarSize,avatarSize);
		.centerCrop()
		.transform(new CircleTransformation())
		.into(ivUserProfilePhoto);
		addHeaderView(vHeader);
		vHeader.setOnClickListener(this);
	}

	public void onClick(View v){
		if(onHeaderClickListener != null){
			onHeaderClickListener.OnGlobalLayoutListener(v);
		}
	}

	public interface OnHeaderClickListener{
		public void onGlobalMenuHeaderClick(View v);
	}

	public void setOnHeaderClickListener(OnHeaderClickListener onHeaderClickListener){
		this.onHeaderClickListener = onHeaderClickListener;
	}
}

private void addDrawerToActivity(DrawerLayout drawerLayout){
	ViewGroup rootView = (ViewGroup) activity.findViewById(android.R.id.content);
	ViewGroup drawerContentRoot = (ViewGroup) drawerLayout.getChildAt(0);
	View contentView = rootView.getChildAt(0);

	rootView.removeView(contentView);

	drawerContentRoot.addView(contentView, new ViewGroup.LayoutParams(
		ViewGroup.LayoutParams.MATCH_PARENT,
		ViewGroup.LayoutParams.MATCH_PARENT
		));

	rootView.addView(drawerLayout, new ViewGroup.LayoutParams(
		ViewGroup.LayoutParams.MATCH_PARENT,
		ViewGroup.LayoutParams.MATCH_PARENT
		));
}

private void setupDrawer(){
	GlobalMenuView menuView = new GlobalMenuView(this);
	menuView.setOnHeaderClickListener(this);

	drawerLayout = DrawerLayoutInstaller.from(this)
	.drawerRoot(R.layout.xxx)
	.drawerLeftView(menuView)
	.drawerLeftWidth(Utils.dpToPx(300))
	.widthNavigationIconToggler(getToolbar())
	.build();
}


// 开发工具 之 tools
// xmlns:tools="http://schemas.android.com/tools"
// There are three Lint attributes in the tools namespace:

// tools:ignore
// tools:targetApi
// tools:locale

<ImageView
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:layout_marginStart="@dimen/margin_main"
android:layout_marginTop="@dimen/margin_main"
android:scaleType="center"
android:src="@drawable/divider"
tools:ignore="contentDescription" />

<ripple xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:tools="http://schemas.android.com/tools"
android:color="@color/accent_color"
tools:targetApi="LOLLIPOP" />

// 八个和UI相关的
// tools:context
// tools:menu
// tools:actionBarNavMode
// tools:listitem/listheader/listfooter
// tools:showIn
// tools:layout


<LinearLayout
xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:tools="http://schemas.android.com/tools"
android:id="@+id/container"
android:layout_width="match_parent"
android:layout_height="match_parent"
android:orientation="vertical"
tools:context="com.android.example.MainActivity">

tools:menu="menu_main,menu_edit"

<merge xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:tools="http://schemas.android.com/tools"
android:layout_width="match_parent"
android:layout_height="match_parent"
tools:showIn="@layout/activity_main">

public void onCreateOptionMenu(Menu menu, MenuInflater inflater){
	super.onCreateOptionMenu(menu, inflater);
	mOptionsMenu = menu;
	inflater.infalte(R.menu.main, menu);
}

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public void setRefreshActionButtonState(boolean refreshing) {
	if (mOptionsMenu == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
		return;
	}

	final MenuItem refreshItem = mOptionsMenu.findItem(R.id.menu_refresh);
	if (refreshItem != null) {
		if (refreshing) {
			refreshItem.setActionView(R.layout.actionbar_indeterminate_progress);
		} else {
			refreshItem.setActionView(null);
		}
	}
}

// google sample 
// spinner interpolator path使用
Interpolator interpolator = mInterpolators[mInterpolatorSpinner.getSelectedItemPosition()];
long duration = mDurationSeekbar.getProgress();
Path path = mIsOut?mPathIn:mPathOut;

startAnimation(interpolator, duration, path);
mIsOut = !mIsOut;

public ObejctAnimator startAnimation(Interpolator interpolator, long duration, Path path){
	ObjectAnimator animator = ObjectAnimator.ofFloat(mView, View.SCAL_X, View.SVALE_Y, path);
	animator.setDuration(duration);
	animator.setInterpolator(interpolator);

	animator.start();

	return animator;
}

mInterpolators = new Interpolator[]{
	new AnimationUtils().loadInterpolator(getActivity(),
		android.R.interpolator.linear),
	new AnimationUtils().loadInterpolator(getActivity(),
		android.R.interpolator.fast_out_linear_in),
	new AnimationUtils().loadInterpolator(getActivity(),
		android.R.interpolator.fast_out_slow_in),
	new AnimationUtils().loadInterpolator(getActivity(),
		android.R.interpolator.linear_out_slow_in)
};

mPathIn = new Path();
mPathIn.moveTo(0.2f, 0.2f);
mPathIn.lineTo(1f, 1f);

        // Path for 'out' animation: shrinking from 100% to 20%
mPathOut = new Path();
mPathOut.moveTo(1f, 1f);
mPathOut.lineTo(0.2f, 0.2f);

@Override
public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate our menu from the resources by using the menu inflater.
	getMenuInflater().inflate(R.menu.main, menu);

        // It is also possible add items here. Use a generated id from
        // resources (ids.xml) to ensure that all menu ids are distinct.
	MenuItem locationItem = menu.add(0, R.id.menu_location, 0, R.string.menu_location);
	locationItem.setIcon(R.drawable.ic_action_location);

        // Need to use MenuItemCompat methods to call any action item related methods
	MenuItemCompat.setShowAsAction(locationItem, MenuItem.SHOW_AS_ACTION_IF_ROOM);

	return true;
}


@Override
public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate our menu from the resources by using the menu inflater.
	getMenuInflater().inflate(R.menu.main, menu);

        // It is also possible add items here. Use a generated id from
        // resources (ids.xml) to ensure that all menu ids are distinct.
	MenuItem locationItem = menu.add(0, R.id.menu_location, 0, R.string.menu_location);
	locationItem.setIcon(R.drawable.ic_action_location);

        // Need to use MenuItemCompat methods to call any action item related methods
	MenuItemCompat.setShowAsAction(locationItem, MenuItem.SHOW_AS_ACTION_IF_ROOM);

	return true;
}

public class FloatingActionButton extends FrameLayout implements Checkable{

	public staitc interface OnCheckedChangeListener{
		void onCkeckChanged(FloatingActionButton fabView, boolean isChecked);
	}

	private static final int[] CHECKED_STATE_SET = { android.R.attr.state_checked};
	private boolean mChecked;
	private OnCheckedChangeListener mOnCheckedChangeListener;

	public FloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
		super(context, attrs, defStyleAttr);

		setClickable(true);

		setOutlineProvider(new ViewOutlineProvider(){
			public void getOutline(View view, Outline outline){
				outline.setOval(0,0,getWidth(),getHeight());
			}
		});

		setClipToOutline(true);
	}
}

public void setChecked(boolean checked) {
	// If trying to set the current state, ignore.
	if (checked == mChecked) {
		return;
	}
	mChecked = checked;

	// Now refresh the drawable state (so the icon changes)
	refreshDrawableState();

	if (mOnCheckedChangeListener != null) {
		mOnCheckedChangeListener.onCheckedChanged(this, checked);
	}
}



@Override
public boolean isChecked() {
	return mChecked;
}

@Override
public void toggle() {
	setChecked(!mChecked);
}

@Override
public boolean performClick() {
	toggle();
	return super.performClick();
}

@Override
protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	super.onSizeChanged(w, h, oldw, oldh);

    // As we have changed size, we should invalidate the outline so that is the the
    // correct size
	invalidateOutline();
}

@Override
protected int[] onCreateDrawableState(int extraSpace) {
	final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
	if (isChecked()) {
		mergeDrawableStates(drawableState, CHECKED_STATE_SET);
	}
	return drawableState;
}

public class GestureListener extends GestureDetector.SimpleOnGestureListener {

	public static final String TAG = "GestureListener";

    // BEGIN_INCLUDE(init_gestureListener)
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
        // Up motion completing a single tap occurred.
		Log.i(TAG, "Single Tap Up");
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
        // Touch has been long enough to indicate a long press.
        // Does not indicate motion is complete yet (no up event necessarily)
		Log.i(TAG, "Long Press");
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
		float distanceY) {
        // User attempted to scroll
		Log.i(TAG, "Scroll");
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
		float velocityY) {
        // Fling event occurred.  Notification of this one happens after an "up" event.
		Log.i(TAG, "Fling");
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
        // User performed a down event, and hasn't moved yet.
		Log.i(TAG, "Show Press");
	}

	@Override
	public boolean onDown(MotionEvent e) {
        // "Down" event - User touched the screen.
		Log.i(TAG, "Down");
		return false;
	}

	@Override
	public boolean onDoubleTap(MotionEvent e) {
        // User tapped the screen twice.
		Log.i(TAG, "Double tap");
		return false;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
        // Since double-tap is actually several events which are considered one aggregate
        // gesture, there's a separate callback for an individual event within the doubletap
        // occurring.  This occurs for down, up, and move.
		Log.i(TAG, "Event within double tap");
		return false;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
        // A confirmed single-tap event has occurred.  Only called when the detector has
        // determined that the first tap stands alone, and is not part of a double tap.
		Log.i(TAG, "Single tap confirmed");
		return false;
	}
    // END_INCLUDE(init_gestureListener)
}


public class BasicGestureDetectFragment extends Fragment{

	public void onActivityCreated(Bundle savedinstanceState){
		super.onActivityCreated(savedinstanceState);
		View gestureView = getActivity().findViewById(R.id.xxx);
		gestureView.setClickable(true);
		gestureView.setFocusable(true);

		GestureDetector.SimpleOnGestureListener gestureListener = new GestureListener();
		final GestureDetector gd = new GestureDetector(getActivity(), gestureListener);
		gestureView.setOnTouchListener(new View.OnTouchListener(){
			public boolean onTouch(View view, MotionEvent motionEvent){
				gd.onTouchEvent(motionEvent);
				return false;
			}
		});
	}
}


// 简单的MVP模式
public class UserBean{
	private String mFirstName;
	private String mLastName;

	public UserBean(String firstName, String lastName){
		this.mFirstName = firstName;
		this.mLastName = lastName;
	}

	public String getFirstName(){
		return mFirstName;
	}

	public String getLastName(){
		return mLastName;
	}

}

public interface IUserModel{
	void setID(int id);
	void setFirstName(String firstName);
	void setLastName(String lastName);

	int getID();

	UserBean load(int id);
}

// 实现省略
public interface IUserView{
	int getID();

	String getFristName();
	String getLastName();

	void setFirstName(String firstName);
	void setLastName(String lastName);
}

// 通过iView 和 iModel 接口操作 model和view
// activity 可以把所有逻辑给presenter处理
public class UserPresenter{
	private IUserView mUserView;
	private IUserModel mUserModel;

	public UserPresenter(IUserView view){
		mUserView = view;
		mUserModel = new mUserModel();
	}

	public void saveUser(int id, String firstName, String lastName){
		mUserModel.setID(id);
		mUserModel.setFirstName(firstName);
		mUserModel.setLastName(lastName);
	}

	public void loadUser(int id){
		UserBean user = mUserModel.load(id);
		mUserView.setFirstName(user.getFirstName());
		mUserView.setLastName(user.getLastName());
	}
}

public class MainActivity extends Activity implements OnClickListener,IUserView{
	UserPresente presenter;
	EditText id,first,last;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedinstanceState);
		setContentView(R.id.xxx);

		findViewById(R.id.xxx).setOnClickListener(this);
		findViewById(R.id.yyy).setOnClickListener(this);

		id = (EditText) findViewById(R.id.xxx);
		first = (EditText) findViewById(R.id.xxx);
		last = (EditText) findViewById(R.id.xxx);

		presenter = new UserPresenter(this);
	}

	public void onClick(View v){
		switch(v.getId()){
			case R.id.save:
			presenter.saveUser(getID(),getFristName(),getLastName());
			break;
			case R.id.load:
			presenter.loadUser(getID());
			break;
			default:
			break;

		}
	}

	public int getID(){
		return new Integer(id.getText().toString());
	}

	public String getFristName(){
		return first.getText().toString();
	}

	public String getLastName(){
		return last.getText().toString();
	}

	public void setFirstName(String firstName){
		first.setText(firstName);
	}

	public void setLastName(String lastName){
		last.setText(lastName);
	}
}


// google sample 多点触摸基本
public final class Pools{
	public static interface Pool<T>{
		public T acquire();
		public boolean release(T insatnce);
	}

	private Pools(){

	}

	public static class SimplePool<T> implements Pool<T>{
		private final Object[] mPool;
		private int mPoolSize;

		public SimplePool(int maxPoolSize){
			if(maxPoolSize <= 0){
				throw new IllegalArgumentException("xxx");
			}
			mPool = new Object[maxPoolSize];
		}

		public T acquire(){
			if(mPoolSize>0){
				final int lastPooledIndex = mPoolSize - 1;
				T instance = (T) mPool[lastPooledIndex];
				mPool[lastPooledIndex] = null;
				mPoolSize--;
				return instance;
			}
			return null;
		}

		public boolean release(T instance){
			if(isInPool(instance)){
				throw new IllegalStateException("xxx");
			}
			if(mPoolSize < mPool.length){
				mPool[mPoolSize] = instance;
				mPoolSize++;
				return true;
			}
			return false;
		}

		private boolean isInPool(T instance){
			for(int i=0; i<mPoolSize; i++){
				return true;
			}
			return false;
		}
	}

	public static class SynchronizedPool<T> extends SimplePool<T>{
		public SynchronizedPool(int maxPoolSize){
			super(maxPoolSize);
		}

		public T acquire(){
			synchronized(mLock){
				return super.acquire();
			}
		}

		public boolean release(T element){
			synchronized(mLock){
				return super.release(element);
			}
		}
	}
}

// 内部类
// 静态内部类 static nested class 封装性和可读性
// 内部类	inner class
// 而静态内部类，则只可以访问外部类的静态方法和静态属性
//（如果是private权限也能访问，这是由其代码位置所决定的），其他则不能访问。
// 而静态内部类是可以独立存在的，即使外部类消亡了，静态内部类还是可以存在的。
// 普通内部类不能声明static的方法和变量，
// 注意这里说的是变量，常量（也就是final static修饰的属性）还是可以的，而静态内部类形似外部类，没有任何限制。
public class TouchDisplayView extends View{

	private SparseArray<TouchHistory> mTouches;
	private boolean mHasTouch = false;

	static final class TouchHistory{
		public static final int HISTORY_COUNT = 20;

		public float x;
		public float y;
		public float pressure = 0f;
		public String label = null;

		public int historyIndex = 0;
		public int historyCount = 0;

		public PointF[] history = new PoinitF[HISTORY_COUNT];

		private static final int MAX_POOL_SIZE = 10;
		private static final SimplePool<TouchHistory> sPool 
		= new SimplePool<TouchHistory>(MAX_POOL_SIZE);

		public static TouchHistory obtain(float x, float y, float pressure){
			TouchHistory data = sPool.acquire();
			if(data == null){
				data = new TouchHistory();
			}
			data.setTouch(x,y,pressure);
			return data;
		}

		public TouchHistory(){
			for( int i=0; i<HISTORY_COUNT; i++){
				history[i] = new PointF();
			}
		}

		public void setTouch(float x, float y, float pressure){
			this.x = x;
			this.y = y;
			this.pressure = pressure;
		}

		public void recycle(){
			this.historyIndex = 0;
			this.historyCount = 0;
			sPool.release(this);
		}

		public void addHistory(float x, float y){
			PointF p = history[historyIndex];
			p.x = x;
			p.y = y;

			historyIndex = (historyIndex + 1)% history.length;

			if(historyCount < HISTORY_COUNT){
				historyCount++;
			}
		}
	}

	public TouchDisplayView(Context context, AttributeSet attrs){
		super(context, attrs);

		mTouches = new SparseArray<TouchHistory>(10);

		initialisePaint();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		final int action = event.getAction();

        /*
         * Switch on the action. The action is extracted from the event by
         * applying the MotionEvent.ACTION_MASK. Alternatively a call to
         * event.getActionMasked() would yield in the action as well.
         */
        switch (action & MotionEvent.ACTION_MASK) {

        	case MotionEvent.ACTION_DOWN: {
                // first pressed gesture has started

                /*
                 * Only one touch event is stored in the MotionEvent. Extract
                 * the pointer identifier of this touch from the first index
                 * within the MotionEvent object.
                 */
                int id = event.getPointerId(0);
                Log.i("temp","ACTION_DOWN-id->" + id);

                TouchHistory data = TouchHistory.obtain(event.getX(0), event.getY(0),
                	event.getPressure(0));
                data.label = "id: " + 0;


                /*
                 * Store the data under its pointer identifier. The pointer
                 * number stays consistent for the duration of a gesture,
                 * accounting for other pointers going up or down.
                 */
                mTouches.put(id, data);

                mHasTouch = true;

                break;
            }

            case MotionEvent.ACTION_POINTER_DOWN: {
                /*
                 * A non-primary pointer has gone down, after an event for the
                 * primary pointer (ACTION_DOWN) has already been received.
                 */

                /*
                 * The MotionEvent object contains multiple pointers. Need to
                 * extract the index at which the data for this particular event
                 * is stored.
                 */
                int index = event.getActionIndex();
                int id = event.getPointerId(index);
                Log.i("temp","ACTION_POINTER_DOWN-id->" + id + ",index->" + index);

                TouchHistory data = TouchHistory.obtain(event.getX(index), event.getY(index),
                	event.getPressure(index));
                data.label = "id: " + id;

                /*
                 * Store the data under its pointer identifier. The index of
                 * this pointer can change over multiple events, but this
                 * pointer is always identified by the same identifier for this
                 * active gesture.
                 */
                mTouches.put(id, data);

                break;
            }

            case MotionEvent.ACTION_UP: {
                /*
                 * Final pointer has gone up and has ended the last pressed
                 * gesture.
                 */

                /*
                 * Extract the pointer identifier for the only event stored in
                 * the MotionEvent object and remove it from the list of active
                 * touches.
                 */
                int id = event.getPointerId(0);
                Log.i("temp","ACTION_UP-id->" + id);
                TouchHistory data = mTouches.get(id);
                mTouches.remove(id);
                data.recycle();

                mHasTouch = false;

                break;
            }

            case MotionEvent.ACTION_POINTER_UP: {
                /*
                 * A non-primary pointer has gone up and other pointers are
                 * still active.
                 */

                /*
                 * The MotionEvent object contains multiple pointers. Need to
                 * extract the index at which the data for this particular event
                 * is stored.
                 */
                int index = event.getActionIndex();
                int id = event.getPointerId(index);
                Log.i("temp","ACTION_POINTER_DOWN-id->" + id + ",index->" + index);
                TouchHistory data = mTouches.get(id);
                mTouches.remove(id);
                data.recycle();

                break;
            }

            case MotionEvent.ACTION_MOVE: {
                /*
                 * A change event happened during a pressed gesture. (Between
                 * ACTION_DOWN and ACTION_UP or ACTION_POINTER_DOWN and
                 * ACTION_POINTER_UP)
                 */

                /*
                 * Loop through all active pointers contained within this event.
                 * Data for each pointer is stored in a MotionEvent at an index
                 * (starting from 0 up to the number of active pointers). This
                 * loop goes through each of these active pointers, extracts its
                 * data (position and pressure) and updates its stored data. A
                 * pointer is identified by its pointer number which stays
                 * constant across touch events as long as it remains active.
                 * This identifier is used to keep track of a pointer across
                 * events.
                 */
                Log.i("temp","ACTION_MOVE-PointerCount->" + event.getPointerCount());
                for (int index = 0; index < event.getPointerCount(); index++) {
                    // get pointer id for data stored at this index
                	int id = event.getPointerId(index);

                    // get the data stored externally about this pointer.
                	TouchHistory data = mTouches.get(id);

                    // add previous position to history and add new values
                	data.addHistory(data.x, data.y);
                	data.setTouch(event.getX(index), event.getY(index),
                		event.getPressure(index));

                }

                break;
            }
        }

        // trigger redraw on UI thread
        this.postInvalidate();

        return true;
    }


    protected void onDraw(Canvas canvas){
    	if(mHasTouch){
    		canvas.drawColor();
    	}else{
    		canvas.drawRect();
    	}

    	for(int i=0; i< mTouches.size(); i++){
    		int id = mTouches.keyAt(i);
    		TouchHistory data = mTouches.valueAt(i);
    		drawCircle(canvas, id, data);
    	}
    }
}

// list pop item
// android.support.v7.widget
class PopupAdapter extends ArrayAdpater<String>{

	PopupAdapter(ArrayList<String> items){
		super(getActivity(), R.layout.list_item, android.R.id.text1, items);
	}

	public View getView(int position,View convertView, ViewGroup container){
		View view = super.getView(position,convertView, container);

		View popupButton = view.findViewById(R.id.xxx);
		popupButton.setTag(getItem(position));
		popupButton.setOnClickListener(PopupListFragment.this);

		return view;
	}

	public void onClick(final View view){
		view.post(new Runnable(){
			public void run(){
				showPopupMenu(view);
			}
		});
	}

	private void showPopupMenu(View view){

		final PopupAdapter adapter = (PopupAdapter)getListAdapter();

		final String item = (String) view.getTag();

		PopupMenu popup = new PopupMenu(getActivity(),view);
		popup.getMenuInflater().infalte(R.menu.popup, popup.getMenu());
		popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
			public boolean onMenuItemClick(MenuItem menuItem){
				switch(menuItem.getItemId()){
					adapter.remove(item);
					return true;
				}
				return false;
			}
		});

		popup.show();
	}
}


// 静态代理
// 代理类和委托类也常常继承同一父类或实现同一接口

class ClassA{
	public void operateMethod1(){};

	public void operateMethod2(){};

	public void operateMethod3(){};
}

// 代理类
public class ClassB{
	private ClassA a;

	public ClassB(ClassA a){
		this.a = a;
	}

	public void operateMethod1(){
		a.operateMethod1();
	}

	public void operateMethod2(){
		a.operateMethod2();
	}
}

// 动态代理
// 1. 新建委托类
// 2. 实现Invocationhandler接口，这是负责连接代理和委托类的中间类必须实现的接口
// 3. 通过Proxy 类新建代理对象

public interface Operate{
	public void operateMethod1();
	public void operateMethod2();
	public void operateMethod3();
}

public class OperateImpl implements Operate{
	public void operateMethod1(){
		sleep(110);
	}

	public void operateMethod2(){
		sleep(120);
	}

	public void operateMethod3(){
		sleep(130);
	}

	private static void sleep(long millSeconds){
		try{
			Thread.sleep(millSeconds);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}

// InvocationHandler 是负责连接代理类和委托类的中间类必须实现的接口
public class TimingInvocationHandler implements InvocationHandler{

	// 委托类对象
	private Object target;

	public TimingInvocationHandler(){
		this.target = target;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
		long start = System.currentTimeMillis();
		Obejct obj = method.invoke(target, args);
		return obj;
	}
}

//调用代理对象的每个函数实际最终都是调用了InvocationHandler的invoke函数
// 通过proxy 类静态函数生成代理对象

public class Main{
	public static void main(Stirng[] args){
		TimingInvocationHandler timingInvocationHandler = new TimingInvocationHandler(new OperateImpl());
		Operate operate = (Operate)(Proxy.newProxyInstance(Operate.class.getClassLoader(),new Class[]{Operate.class},timingInvocationHandler));

		operate.operateMethod1();
		operate.operateMethod2();
		operate.operateMethod3();
	}
}

// Fragment 优秀实践
public class ContentFragment extends Fragment{
	private String mArgument;
	public static final String ARGUMENT = "argument";

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedinstanceState);

		Bundle bundle = getArguments();
		if(bundle != null){
			mArgument = bundle.getString(ARGUMENT);
		}
	}

	public static ContentFragment newInstance(String argument){
		Bundle bundle = new Bundle();
		bundle.putString(ARGUMENT, argument);
		ContentFragment contentFragment = new ContentFragmentc();
		contenxtFragment.setArguments(bundle);
		return contentFragment;
	}
}

// 在Fragment中存在startActivityForResult（）以及onActivityResult（）方法，
// 但是呢，没有setResult（）方法，用于设置返回的intent，
// 这样我们就需要通过调用getActivity().setResult(ListTitleFragment.REQUEST_DETAIL, intent);。


public class ListTitleFragment extends ListFragment{
	public static final int REQUEST_DETAIL = 0x110;
	private List<String> mTitles = Arrays.asList("xxx","xxx","xxx");
	private int mCurrentPos;
	private ArrayAdapter<String> mAdapter;

	public void onActivityCreated(Bundle savedinstanceState){
		super.onActivityCreated(savedinstanceState);
		setListAdapter(mAdapter = new ArrayAdapter<String>(
			getActivity(),android.R.layout.simple_list_item_1, mTitles));
	}

	public void onListItemClick(ListView l, View v, int position, long id){
		mCurrentPos = position;
		Intent intent = new Intent(getActivity(),ContentActivity.class);
		intent.putExtra(ConentFragment.ARGUMENT,mTitles.get(position));
		startActivityForResult(intent,REQUEST_DETAIL);
	}

	public void onActivityResult(int requestCode,int resultCode,Intent data){
		super.xxx();
		if(requestCode == REQUEST_DETAIL){
			mTitles.set(mCurrentPos,mTitles.get(mCurrentPos)+" -" + data.getStringExtra(ContentFragment.RESPONSE));
			mAdpater.notifyDataSetChanged();
		}
	}
}

public class ContentFragment extends Fragment{

	private String mArgument;
	public static final String ARGUMENT = "argument";
	public static final String RESPONSE = "response";

	public void onCreate(Bundle savedinstanceState){
		super.xxx();

		Bundle bundle = getArgument();
		if(bundle != null){
			mArgument = bundle.getString(ARGUMENT);
			Intent intent = new Intent();
			intent.putExtra(RESPONSE,"god");
			getActivity().setResult(ListTitleFragment.REQUEST_DETAIL,intent);
		}
	}
}

// 抽象出一个加载fragment的activity
public abstract class SingleFragmentActivity extends FragmentActivity{

	protected abstract Fragment createFragment();

	protected void onCreata(Bundle savedInstanceState){
		super.xxx();
		setContentView(R.layout.xxx);

		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.xxx);
		if(fragment == null){
			fragment = createFragment();
			fm.beginTransaction().add(R.id.xxx,fragment).commit();
		}

	}
}

public class ContentActivity extends SingleFragmentActivity  
{  
	private ContentFragment mContentFragment;  

	@Override  
	protected Fragment createFragment()  
	{  
		String title = getIntent().getStringExtra(ContentFragment.ARGUMENT);  

		mContentFragment = ContentFragment.newInstance(title);  
		return mContentFragment;  
	}  
}

// fragment 里面的dialog
public void onClick(View v){
	EvaluateDialog = new EvaluateDialog();
	dialog.setTargetFragment(ContentFragment.this, REQUEST_DETAIL);
	dialog.show(getFragmentManager(),EVALUATE_DIALOG);
}

// 我们调用了Fragment.setTargetFragment ，
// 这个方法，一般就是用于当前fragment由别的fragment启动

public class EvaluateDialog extends DialogFragment{
	private String[] mEvaluateVals = new String[]{"xxx","xxx","xxx"};
	public static final String RESPONSE_EVALUATE = "response_evaluate";

	public Dialog onCreateDialog(Bundle savedinstanceState){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Evaluate")
		.setItems(mEvaluateVals,
			new OnClickListener(){
				public void onClick(DialogInterface dialog, int which){
					setResult(which);
				}
			})
		return builder.create();
	}

	protected void setResult(int which){
		if(getTargetFragment() == null){
			return;
		}

		Intent intent = new Intent();
		intent.putExtra(xxx,xxx);
		getTargetFragment().onActivityResult(ContentFragment.REQUEST_EVALUATE,Activity.RESULT_OK, intent);
	}
}


// SurfaceView
public class GameFlabbyBird extends SurfaceView implements Callback,Runnable{
	private SurfaceHolder mHolder;
	private Canvas mCanvas;
	private Thread t;
	private boolean isRunning;

	public GameFlabbyBird(Context context){
		this(context,null);
	}

	public GameFlabbyBird(Context context,AttributeSet attrs){
		super(context, attrs);

		mHolder = getHolder();
		mHolder.addCallback(this);

		setZOrderOnTop(true);
		mHolder.setFormat(PixelFormat.TRANSLUCENT);

		setFocusable(true);
		setFocusableInTouchMode(true);
		this.setKeepScreenOn(true);
	}

	public void surfaceCreated(SurfaceHolder holder){
		isRunning = true;
		t = new Thread(this);
		t.start();
	}

	public void surfaceChanged(SurfaceHolder holder,int format, int width,int height){

	}

	public void surfaceDestroyed(SurfaceHolder holder){
		isRunning = false;
	}

	public void run(){
		while(isRunning){
			long start = System.currentTimeMillis();
			draw();
			long end = System.currentTimeMillis();

			// ....
		}
	}

	private void draw(){
		try{
			mCanvas = mHolder.lockCanvas();
			if(mCanvas != null){
				// drawing...
			}
		}catch(Exception e){

		}finally{
			if(mCanvas != null){
				mHolder.unlockCanvasAndPost(mCanvas);
			}
		}
	}
}


// multiSwipeRefresh

<com.example.android.swiperefreshmultipleviews.MultiSwipeRefreshLayout
xmlns:android="http://schemas.android.com/apk/res/android"
android:id="@+id/swiperefresh"
android:layout_width="match_parent"
android:layout_height="match_parent">

<FrameLayout
android:layout_width="match_parent"
android:layout_height="match_parent">

<GridView
android:id="@android:id/list"
android:layout_width="match_parent"
android:layout_height="match_parent"
android:numColumns="2" />

<TextView
android:id="@android:id/empty"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text="@string/empty_text"
android:layout_gravity="center"/>

</FrameLayout>

</com.example.android.swiperefreshmultipleviews.MultiSwipeRefreshLayout>

public class MultiSwipeRefreshLayout extends SwipeRefreshLayout{
	private View[] mSwipeableChildren;

	public MultiSwipeRefreshLayout(Context context){
		super(context);
	}

	public MultiSwipeRefreshLayout(Context context,AttributeSet attrs){
		super(context,attrs);
	}

	public void setSwipeableChildren(final int... ids){
		assert ids != null;

		mSwipeableChildren = new View[ids.length];
		for(int i=0; i<ids.length; i++){
			mSwipeableChildren[i] = findViewById(ids[i]);
		}
	}

	public boolean canChildScrollUp(){
		if(mSwipeableChildren != null && mSwipeableChildren.length > 0){
			for( View view: mSwipeableChildren){
				if( view != null && view.isShown() && !canViewScroollUp(view)){
					return false;
				}
			}
		}
		return true;
	}

	private static boolean canViewScrollUp(View view){
		if( android.os.Build.VERSION.SDK_INT > 14){
			return ViewCompat.canScrollVertically(view,-1);
		}else{
			if(view instanceof AbsListView){
				final AbsListView listView = (AbsListView) view;
				return listView.getChildCount() >0 || listView.getChildAt(0).getTop()< listView.getPaddingTop());
}else{
	return view.getScrollY()>0;
}

}
}

}

public class SwipeRefreshMultipleViewsFragment extends Fragment {

	private static final String LOG_TAG = SwipeRefreshMultipleViewsFragment.class.getSimpleName();

	private MultiSwipeRefreshLayout mSwipeRefreshLayout;
	private GridView mGridView;
	private ArrayAdapter<String> mListAdapter;
	private View mEmptyView;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_sample, container, false);

		mSwipeRefreshLayout = (MultiSwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
		mGridView = (GridView) view.findViewById(android.R.id.list);
		mEmptyView = view.findViewById(android.R.id.empty);

		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		mListAdapter = new ArrayAdapter<String>(
			getActivity(),
			android.R.layout.simple_list_item_1,
			android.R.id.text1);

		mGridView.setAdapter(mListAdapter);

		mGridView.setEmptyView(mEmptyView);

		mSwipeRefreshLayout.setSwipeableChildren(android.R.id.list, android.R.id.empty);
		mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				initiateRefresh();
			}
		});
	}

	private void initiateRefresh() {
		new DummyBackgroundTask().execute();
	}

	private class DummyBackgroundTask extends AsyncTask<Void,Void,List<String>>{
    	static final int TASK_DURATION = 3 * 1000; // 3 seconds

    	protected List<String> doInBackground(Void... params){
    		return Cheeses.randomList(xxx);
    	}

    	protected void onPostExecute(List<String> result){
    		super.onPostExecute(result);

    		onRefreshComplete(result);
    	}
    }

    private void onRefreshComplete(List<String> result){
    	mListAdapter.clear();
    	for(String cheese: result){
    		mListAdapter.add(cheese);
    	}

    	mSwipeRefreshLayout.setRefreshing(false);
    }
}

public class Cheeses{
	static final String[] CHEESES = {"aa","bb"};

	public static ArrayList<String> asList(){
		ArrayList<String> items = new ArrayList<String>();
		for(int i=0, z= CHEESES.length; i<z; i++){
			items.add(CHEESES[i]);
		}
		return items;
	}
}

public static ArrayList<String> randomList(int count){
	Random random = new Random();
	HashSet<String> items = new HashSet<String>();

	count = Math.min(count, CHEESES.length);

	while(items.size() < count){
		items.add(CHEESES[random.nextInt(CHEESES.length)]);
	}

	return new ArrayList<String>(items);
}

// recyclerView 
public class RecyclerViewFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.recycler_view_frag, container, false);
		rootView.setTag(TAG);

		mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
		mLayoutManager = new LinearLayoutManager(getActivity());
		setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
		mAdapter = new CustomAdapter(mDataset);
		mRecyclerView.setAdapter(mAdapter);
	}

	public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType){
		int scrollPosition = 0;

		if(mRecyclerView.getLayoutManager() != null){
			scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFristCompletelyVisibleItemPosition();
		}

    	// swithc() ...

		mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
		mLayoutManager = new LinearLayoutManager(getActivity());

		mRecyclerView.setLayoutManager(mLayoutManager);
		mRecyclerView.scrollToPosition(scrollPosition);
	}
}

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{

	private static final String TAG = "xxx";
	private String[] mDataSet;

	public static class ViewHolder extends RecyclerView.ViewHolder{
		private final TextView textView;

		public ViewHolder(View v){
			super(v);
			v.setOnClickListener(new View.OnClickListener(){
				public void onClick(View v){
					// ...
				}
			});
			textView = (TextView) v.findViewById(R.id.textView);
		}

		public TextView getTextView(){
			return textView;
		}
	}

	public CustomAdapter(String[] dataSet){
		mDataSet = dataSet;
	}

	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.xxx,viewGroup,false);
		return new ViewHolder(v);
	}

	public void onBindViewHolder(ViewHolder viewHolder, final int position){
		viewholder.getTextView().setText(mDataset[position]);
	}

	public int getItemCount(){
		return mDataSet.length;
	}
}

// 官方比较菜的tabs + viewpager
protected void onCreat(Bundle savedInstanceState){
	super.onCreate(savedinstanceState);

	final ActionBar actionBar = getActionBar();
	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

	mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

	mViewPager = (ViewPager) findViewById(R.id.pager);
	mViewPager.setAdapter(mSectionsPagerAdapter);

	mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
		public void onPageSelected(int position){
			actionBar.setSelectedNavigationItem(position);
		}
	});

	for(int i=0; i<mSectionsPagerAdapter.getCount(); i++){
		actionBar.addTab(actionBar.newTab().setText(mSelectedPosition.getPageTitle(i)).setTabListener(this));
	}
}

public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction){
	mViewPager.setCurrentItem(tab.getPosition());
}

public void onTabUnselected(ActionBar.Tab tab,FragmentTransaction fragmentTransaction){

}

public void onTabReselected(ActionBar.Tab tab,FragmentTransaction fragmentTransaction){

}

public class SectionsPagerAdapter extends FragmentPagerAdapter{

	public SectionsPagerAdapter(FragmentManager fm){
		super(fm);
	}

	public Fragment getItem(int position){
		Fragment fragment = new DummySectionFragment();
		Bundle args = new Bundle();
		args.putInt(DummySectionFragment.xxx,position + 1);
		fragment.setArguments(args);
		return fragment;
	}

	public int getCount(){
		return 3;
	}

	public CharSequence getPageTitle(int position){
		Locale l = Locale.getDefault();
		switch(position){
			case 0:
			return xx;
			///
		}

		return null;
	}

	public static class DummySectionFragment extends Fragment {
		
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

// actionbar donebar donebutton
public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

	private Sample[] mSamples;
	private GridView mGridView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        // Prepare list of samples in this dashboard.
		mSamples = new Sample[]{
			new Sample(R.string.donebaractivity_title, R.string.donebaractivity_description,
				DoneBarActivity.class),
			new Sample(R.string.donebuttonactivity_title, R.string.donebuttonactivity_description,
				DoneButtonActivity.class),
		};

        // Prepare the GridView
		mGridView = (GridView) findViewById(android.R.id.list);
		mGridView.setAdapter(new SampleAdapter());
		mGridView.setOnItemClickListener(this);
	}

	public void onItemClick(AdapterView<?> container, View view, int position, long id){
		startActivity(mSamples[position].intent);
	}

	private class SampleAdapter extends BaseAdapter{

		public int getCount(){
			return mSamples.length;
		}

		public Object getItem(int position){
			return mSamples[position];
		}

		public long getItemId(int position){
			return mSamples[position].hashCode();
		}

		public View getView(int position, View convertView, ViewGroup container){
			if(convertView == null){
				convertView = getLayoutInflater().inflate(R.layout.xxx,container,false);
			}

			((TextView) convertView.findViewById(android.R.id.text1).setText(mSamples[position].titleResId));
			((TextView) convertView.findViewById(android.R.id.text2).setText(mSamples[position].titleResId));

			return convertView;
		}
	}

	private class Sample{
		int titleResId;
		int descriptionResId;
		Intent intent;

		private Sample(int titleResId, int descriptionResId, Intent intent){
			this.intent = intent;
			this.titleResId = titleResId;
			this.descriptionResId = descriptionResId;
		}

		private Sample(int titleResId, int descriptionResId, Class<? extends Activity> activityClass){
			this(titleResId, descriptionResId, new Intent(MainActivity.this, activityClass));
		}
	}
}

<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
android:layout_width="match_parent"
android:layout_height="wrap_content">
<!-- The CardView needs to be wrapped to ensure spacing is applied correctly. -->

<android.support.v7.widget.CardView
style="@style/Widget.SampleDashboard.Card"
android:layout_width="match_parent"
android:layout_height="wrap_content">

<LinearLayout
style="@style/Widget.SampleDashboard.Item"
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:orientation="vertical">

<TextView
android:id="@android:id/text1"
style="@style/Widget.SampleDashboard.Item.Title"
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:text="Hello world" />

<TextView
android:id="@android:id/text2"
style="@style/Widget.SampleDashboard.Item.Description"
android:layout_width="match_parent"
android:layout_height="wrap_content" />

</LinearLayout>

</android.support.v7.widget.CardView>

</FrameLayout>

public class DoneBarActivity extends Activity{

	public void onCreate(Bundle savedInstanceState){
		super.xxx(xxx);

		final LayoutInflater inflater = (LayoutInflater) getActionBar().getThemedContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		final View customActionBarView = inflater.inflate(R.layout.xxx,null);

		customActionBarView.findViewById(R.id.actionbar_done).setOnClickListener(
			new View.OnClickListener(){
				public void onClick(View v){
					finish();
				}
			});

		customActionBarView.findViewById(R.id.actionbar_cancel).setOnClickListener(
			new View.OnClickListener(){
				public void onClick(View v){
					finish();
				}
			});

		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM, 
			ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
		actionBar.setCustomView(customActionBarView,
			new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));

		setContentView(R.layout.activity_done_bar);
	}
}

public class DoneButtonActivity extends Activity{

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final LayoutInflater inflater = (LayoutInflater) getActionBar().getThemedContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		final View customActionBarView = inflater.inflate(R.layout.actionbar_custom_view_done, null);

		customActionBarView.findViewById(R.id.actionbar_done).setOnClickListener(
			new View.OnClickListener() {
				@Override
				public void onClick(View v) {
                        // "Done"
					finish();
				}
			});

		final ActionBar actionBar = getActionBar();

		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,
			ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME| ActionBar.DISPLAY_SHOW_TITLE);
		actionBar.setCustomView(customActionBarView);
        // END_INCLUDE (inflate_set_custom_view)

		setContentView(R.layout.activity_done_button);
	}
}

<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
android:layout_width="match_parent"
android:layout_height="match_parent"
android:orientation="horizontal"
android:divider="?android:attr/dividerVertical"
android:showDividers="middle"
android:dividerPadding="12dp">

<include layout="@layout/include_cancel_button" />
<include layout="@layout/include_done_button" />
</LinearLayout>

<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
android:layout_width="match_parent"
android:layout_height="match_parent"
android:orientation="horizontal"
android:divider="?android:attr/dividerVertical"
android:showDividers="end"
android:dividerPadding="12dp">

<include layout="@layout/include_done_button" />
</LinearLayout>

<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
style="?android:actionButtonStyle"
android:id="@+id/actionbar_cancel"
android:layout_width="0dp"
android:layout_height="match_parent"
android:layout_weight="1">

<TextView style="?android:actionBarTabTextStyle"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:layout_gravity="center"
android:paddingRight="20dp"
android:drawableLeft="@drawable/ic_action_cancel"
android:drawablePadding="8dp"
android:gravity="center_vertical"
android:text="@string/cancel" />
</FrameLayout>


public class Greetings{
	public static void main(String[] args){
		for( int i=0; i<3; i++){
			// ...
		}
	}
}

for(i in 0..2){ ... }

// Groovy
// • 轻量级Java
// • 返回声明几乎是可选的 .
// • 几乎是可选的 虽然可以用于单行声明 
// • Methods（方法）和 Classes（类） 默认为 public. 
// • ?.运算符只在对象应用不为空时调用. 
// • 初始化 JavaBeans 可以使用命名参数. 
// • 如果你不想，它不会强迫你捕获异常. 他们传递给调用的代码. 
// • 你可以使用内部 static 方法来引用这个 Class 对象. 

	class Wizard{
		def static learn(trick,action){
		// ...
			this
		}
	}

Wizard.learn('aaa',{/**/})
.learn('xxx',{/**/})
.learn('xxx',{/**/})

// 简化匿名内部类
button.addActionListener{
	{JOptionPane.showMessageDialog(frame,"xxx")} as ActionListener
}

displayMouseLocation = {positionLabel.setText("$it.x,$it.y")}
frame.addMouseListener(displayMouseLocation as MouseListener)
frame.addMouseMotionListener(displayMouseLocation as MouseMotionListener)

// 简单的创建一个 Map—简单的使用(:)来
// 分隔方法名和代码块. 
handleFocus = [
focusGained:{msgLabel.setText("Good to see u")},
focusLost:{msgLabel.setText("Come back soon")}
]
button.addFocusListener(handleFocus as FoucusListener)

// 如果把一个对象放在一个boolean表达式中，
// 会检查这个对象是否为null，为null则为true

// 一些方法重载
lst = ['hello']
lst<<'there'
println lst
["hello","there"]

//
public void takeHelp(Man man){
	man.helpMoveThings();
}

public void takeHelp(Human human){
	human.helpMoveThings();
}

public interface Helper{
	public void helpMoveThings();
}

public void takeHelp(Helper helper){
	helper.helpMoveThings();
}

// Groovy
def takeHelp(helper){
	helper.helpMoveThings();
}
// 在这里 helper 并没有
// 显示的实现任何接口,其实是使用了对象隐式接口能力

takeHelp(new Man());

// 闭包相关
def sum(n){
	total = 0
	for(int i=2; i<=n; i+=2){
		total += i
	}
	total
}
println "Sum of even numbers from 1 to 10 is ${sum(10)}"

def product(n){
	prod = 1
	for(int i=2; i<=n; i+=2){
		prod *= i
	}
	prod
}
println "Product of even numbers from 1 to 10 is ${product(10)}"

def sqr(n){
	squared = []
	for(int i=2; i<=n; i+=2){
		squared<< i**2
	}
	squared
}
println "Squares of even numbers from 1 to 10 is ${sqr(10)}"

//Groovy的方式
def pickEven(n,block){
	for(int i=2; i<=n; i+=2){
		block(i);
	}
}
pickEven(10, {println it})
// 这个可变的代码块持有
// 一个对闭包的引用。 正如你传递对象一样，你也可以传递闭包。
// 如果方法的最后一个参数是闭包，可以简化如下
pickEven(10){println it}

// Groovy 中的代码块不像 Java，它不能单独存在，
// 必须依附于一个方法或赋值给一个命名变量。

// 假如你只传递一个参数给代码块，
// 那么这个参数在代码块中就可以使用 it 来引用。
// 也可以自己定义名字
pickEven(10){evenNumber->println evenNumber}

total = 0
pickEven(10) { total += it }
println "Sum of even numbers from 1 to 10 is ${total}"

// 闭包就是一个拥有参数的函数被绑定到上下文环境中来运行

// Gradle
// Gradle是领域驱动设计的构建工具
// 任何一个Gradle构建，都是由一个或多个project组成的。
// Project就是你想要用Gradle做什么
// 一个project由多个task组成
// 每个task代表了构建过程当中的一个原子性操作
// 每个task由多个action组成
// 比如编译 打包 生成javadoc 发布等等

// Task是Gradle里定义的一个接口
// 它定义了一系列的诸如doLast, doFirst等抽象方法
// 这其实是Gradle利用了Groovy的操作符重载的特性，
// 把左位移操作符实现为将action加到task的最后，相当于调用doLast方法

apply plugin: 'com.android.application'  
// 以上语句中的apply是一个方法，给它传递了一个参数plugin，
// plugin 的值是'com.android.application'。如果有多个参数，则以逗号隔开

// Groovy中花括号包含的部分成为一个闭包（Closure）

compileOptions {  
	sourceCompatibility JavaVersion.VERSION_1_7  
	targetCompatibility JavaVersion.VERSION_1_7  
} 

// compileOptions 是一个 Method， 它的参数是一个闭包

// 可穿戴
<android.support.wearable.view.WatchViewStub
xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto"
xmlns:tools="http://schemas.android.com/tools"
android:id="@+id/watch_view_stub"
android:layout_width="match_parent"
android:layout_height="match_parent"
app:rectLayout="@layout/rect_activity_wear"
app:roundLayout="@layout/round_activity_wear">
</android.support.wearable.view.WatchViewStub>

// 渲染布局文件不是同步的，所以要设置一个回调来监听WatchViewStub渲染完成。
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_wear);

	WatchViewStub stub = (WatchViewStub)findViewById(R.id.watch_view_stub);
	stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener(){
		public void onLayoutInflated(WatchViewStub stub){
			TextView tv = (TextView) stub.findViewById(R.id.text);
		}
	});
}

// Wearable UI库中包含的类BoxInsetLayout继承自FrameLayout，
// 能让你定义一个单一布局，来同时适用于方形和圆形屏幕
// 为了显示进这个区域，子view需要设定layout_box属性，
// top, bottom, left和right可以结合使用，例如"left|top"
<android.support.wearable.view.BoxInsetLayout
xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto"
android:background="@drawable/robot_background"
android:layout_height="match_parent"
android:layout_width="match_parent"
android:padding="15dp">

<FrameLayout
android:layout_width="match_parent"
android:layout_height="match_parent"
android:padding="5dp"
app:layout_box="all">
<TextView
android:gravity="center"
android:layout_height="wrap_content"
android:layout_width="match_parent"
android:text="@string/sometext"
android:textColor="@color/black" />

<ImageButton
android:background="@null"
android:layout_gravity="bottom|left"
android:layout_height="50dp"
android:layout_width="50dp"
android:src="@drawable/ok" />

<ImageButton
android:background="@null"
android:layout_gravity="bottom|right"
android:layout_height="50dp"
android:layout_width="50dp"
android:src="@drawable/cancel" />
</FrameLayout>
</android.support.wearable.view.BoxInsetLayout>


// CardFrame 只能包含一个直接子view，你可以添加其他自定义内容插入到卡片中。

// 创建CardFragment 的步骤：
// 1、在你的布局中，分配一个id给CardFragment
// 2、在你的activity中新建一个CardFragment 实例
// 3、使用fragment manager添加CardFragment 实例
<android.support.wearable.view.BoxInsetLayout
xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto"
android:background="@drawable/robot_background"
android:layout_height="match_parent"
android:layout_width="match_parent">

<FrameLayout
android:id="@+id/frame_layout"
android:layout_width="match_parent"
android:layout_height="match_parent"
app:layout_box="bottom">
</FrameLayout>
</android.support.wearable.view.BoxInsetLayout>

protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_wear_activity2);

	FragmentManger fragmentManager = getFragmentManager();
	FragmentTransaction fragmentTransaction = fragmentManger.beginTransaction();
	CardFragment cardFragment = CardFragment.create(getString(R.string.cftitle),getString(R.string.cfdesc),R.drawable.p);
	fragmentTransaction.add(R.id.frame_layout,cardFragment);
	fragmentTransaction.commit();
}

// 如果要使用CardFragment来创建一个自定义布局的卡片，
// 可以继承这个类然后onCreateContentView方法。
<android.support.wearable.view.BoxInsetLayout
xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto"
android:background="@drawable/robot_background"
android:layout_height="match_parent"
android:layout_width="match_parent">

<android.support.wearable.view.CardScrollView
android:id="@+id/card_scroll_view"
android:layout_height="match_parent"
android:layout_width="match_parent"
app:layout_box="bottom">

<android.support.wearable.view.CardFrame
android:layout_height="wrap_content"
android:layout_width="fill_parent">

<LinearLayout
android:layout_height="wrap_content"
android:layout_width="match_parent"
android:orientation="vertical"
android:paddingLeft="5dp">

<TextView
android:fontFamily="sans-serif-light"
android:layout_height="wrap_content"
android:layout_width="match_parent"
android:text="@string/custom_card"
android:textColor="@color/black"
android:textSize="20sp"/>

<TextView
android:fontFamily="sans-serif-light"
android:layout_height="wrap_content"
android:layout_width="match_parent"
android:text="@string/description"
android:textColor="@color/black"
android:textSize="14sp"/>
</LinearLayout>
</android.support.wearable.view.CardFrame>
</android.support.wearable.view.CardScrollView>
</android.support.wearable.view.BoxInsetLayout>

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_wear_activity2);

	CardScrollView cardScrollView =(CardScrollView) findViewById(R.id.card_scroll_view);
	cardScrollView.setCardGravity(Gravity.BOTTOM);
}

// Wearable UI库包含WearableListView 类，是专为穿戴设备优化的实现类。
// 创建列表的步骤：
// 1、在你activity的布局中添加WearableListView 元素
// 2、创建一个自定义布局来实现你的list item
// 3、创建一个adapter装载进这个listview
<android.support.wearable.view.BoxInsetLayout
xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto"
android:background="@drawable/robot_background"
android:layout_height="match_parent"
android:layout_width="match_parent">

<FrameLayout
android:id="@+id/frame_layout"
android:layout_height="match_parent"
android:layout_width="match_parent"
app:layout_box="left|bottom|right">

<android.support.wearable.view.WearableListView
android:id="@+id/wearable_list"
android:layout_height="match_parent"
android:layout_width="match_parent">
</android.support.wearable.view.WearableListView>
</FrameLayout>
</android.support.wearable.view.BoxInsetLayout>

// 下面这个布局实现了WearableListView.Item接口，
// 并能在列表滚动的时候，让标题和图标带动画效果

// 创建listItem布局
public class WearableListItemLayout extends LinearLayout implements WearableListView.item{

	private final float mFadedTextAlpha;
	private final int mFadedCircleColor;
	private final int mChosenCircleColor;

	private ImageView mCircle;
	private float mScale;
	private TextView mName;

	public WearableListItemLayout(Context context){
		this(context, null);
	}

	public WearableListItemLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public WearableListItemLayout(Context context,AttributeSet attrs, int defStyle){
		super(context,attrs,defStyle);

		mFadedTextAlpha = getResources().getInteger(R.integer.action_text_faded_alpha) / 100f;
		mFadedCircleColor = getResources().getColor(R.color.grey);
		mChosenCircleColor = getResources().getColor(R.color.blue);
	}

	protected void onFinishInflate(){
		super.onFinishInflate();

		mCircle = (ImageView)findViewById(R.id.circle);
		mName = (TextView) findViewById(R.id.name);
	}

	public float getProximityMinValue(){
		return 1f;
	}

	public float getProximityMaxValue(){
		return 1.6f;
	}

	public float getCurrentProximityValue(){
		return mScale;
	}

	public void setScalingAnimatorValue(float scale){
		mScale = scale;
		mCircle.setScaleX(scale);
		mCircle.setScaleY(scale);
	}

	public void onScaleUpStart(){
		mName.setAlpha(1f);
		((GradientDrawable) mCircle.getDrawable()).setColor(mChosenCircleColor);
	}

	public void onScaleDownStart(){
		((GradientDrawable) mCircle.getDrawable()).setColor(mFadedCircleColor);
		mName.setAlpha(mFadedTextAlpha);
	}
}

// 创建listitem布局
<com.example.android.support.wearable.notifications.WearableListItemLayout
xmlns:android="http://schemas.android.com/apk/res/android"
android:gravity="center_vertical"
android:layout_width="match_parent"
android:layout_height="80dp">

<ImageView
android:id="@+id/circle"
android:layout_height="20dp"
android:layout_margin="16dp"
android:layout_width="20dp"
android:src="@drawable/wl_circle"/>
<TextView
android:id="@+id/name"
android:gravity="center_vertical|left"
android:layout_width="wrap_content"
android:layout_marginRight="16dp"
android:layout_height="match_parent"
android:fontFamily="sans-serif-condensed-light"
android:lineSpacingExtra="-4sp"
android:textColor="@color/text_color"
android:textSize="16sp"/>
</com.example.android.support.wearable.notifications.WearableListItemLayout>

// 创建adapter
// adapter用于对listview的内容填充

private static final class Adapter extends WearableListView.Adapter{
	private String[] mDataset;
	private final Context mContext;
	private final LayoutInflater mInflater;

	public Adapter(Context context, String[] dataset){
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mDataset = dataset;
	}

	pulic static class ItemViewHolder extends WearableListView.ViewHolder{
		private TextView textView;

		public ItemViewHolder(View itemView){
			super(itemView);
			textView = (TextView)itemView.findViewById(R.id.name);
		}
	}

	public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
		return new ItemViewHolder(mInflater.infalte(R.layout.list_item), null);
	}

	public void onBindViewHolder(WearableListView.ViewHolder holder, int position){
		ItemViewHolder itemHolder = (ItemViewHolder)holder;
		TextView view = itemHolder.textView;
		view.setText(mdataset[position]);
		holder.itemView.setTag(position);
	}

	public int getItemCount(){
		return mDataset.lenghth;
	}
}

// 关联adapter并设置点击事件
public class WearActivity extends Activity implements WearableListView.ClickListener{
	string[] elements = {"List Item 1", "List Item 2", ...};

	protected void onCreate(Bundle savedinstanceState){
		super.onCreate(savedinstanceState);
		setContentView(R.layout.my_list.activity);

		WearableListView listView = (WearableListView)findViewById(R.id.wearable_list);
		listView.setAdapter(new Adapter(this, elements));

		listView.setClickListener(this);
	}

	public void onClick(WearableListView.ViewHolder v){
		Integer tag = (Integer) v.itemView.getTag();
		// ...
	}

	public void onTopEmptyRegionClick(){

	}
}

// 需要在你的activity布局中添加GridViewPager 元素，
// 并继承FragmentGridPagerAdapter 类来实现一个adapter提供页面内容
<android.support.wearable.view.GridViewPager
xmlns:android="http://schemas.android.com/apk/res/android"
android:id="@+id/pager"
android:layout_width="match_parent"
android:layout_height="match_parent" />

public class SampleGridPagerAdapter extends FragmentGridPagerAdapter{

	private final Context mContext;

	public SampleGridPagerAdapter(Context ctx,FragmentManager fm){
		super(fm);
		mContext = ctx;
	}

	static final int[] BG_IMAGES = new int[]{
		R.drawable.xx,...
		R.drawable.yy
	};

	private static class Page{
		int titleRes;
		int textRes;
		int iconRes;
	}

	private final Page[][] PAGES = {...};

	// Override methods in FragmentGridPagerAdapter
	public Fragment getFragment(int row, int col){
		Page page = PAGES[row][col];
		String title = page.textRes !=0 ? mContext.getString(page.titleRes):null;
		String text = page.textRes != 0 ? mContext.getString(page.textRes) : null;
		CardFragment fragment = CardFragment.create(title,text,page.iconRes);

		fragment.setCardGravity(page.cardGravity);
		fragment.setExpansionEnabled(page.expansionDirection);
		fragment.setExpansionFactor(page.expansionFactor);
		return fragment;
	}

	public ImageReference getBackground(int row, int column){
		return ImageReference.forDrawable(BG_IMAGES[row % BG_IMAGES.length]);
	}

	public int getRowCount(){
		return PAGES.length;
	}

	public int getColumnCount(int rowNum){
		return PAGES[rowNum].length;
	}
}

// picker通过调用getFragment 和getBackground 方法
// 来获取grid中相应位置的视图和背景。
public class MainActivity extends Activity{
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
	pager.setAdapter(new  SampleGridPagerAdapter(this,getFragmentManager()));
}


// 添加定时器的步骤：
// 1、在你的布局中添加一个DelayedConfirmationView 元素
// 2、在你的activity中实现DelayedConfirmationListener 接口
// 3、设置定时器时长并在用户完成操作的时候启动它

<android.support.wearable.view.DelayedConfirmationView
android:id="@+id/delayed_confirm"
android:layout_width="40dp"
android:layout_height="40dp"
android:src="@drawable/cancel_circle"
app:circle_border_color="@color/lightblue"
app:circle_border_width="4dp"
app:circle_radius="16dp">
</android.support.wearable.view.DelayedConfirmationView>

public class WearActivity extends Activity implements DelayedConfirmationView.DelayedConfirmationListener{

	private DelayedConfirmationView mDelayedView;

	protected void onCreate(Bundle savedinstanceState){
		super.xx;
		setContextView(xx);

		mDelayedView = (DelayedConfirmationView) findViewById(R.id.delayed_confirm);
		mDelayedView.setListener(this);
	}

	public void onTimerFinished(View view){

	}

	public void onTimerSelected(View view){

	}

	// 启动定时器
	mDelayedView.setTotalTimeMs(2000);
	mDelayedView.start();
}

// 当用户在你的应用中完成操作的时候，创建一个intent来启动ConfirmationActivity 。
// 你可以设置EXTRA_ANIMATION_TYPE 来定义动画类型：
// SUCCESS_ANIMATION FAILURE_ANIMATION OPEN_ON_PHONE_ANIMATION

Intent intent = new Intent(this, ConfirmationActivity.class);
intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,ConfirmationActivity.SUCCESS_ANIMATION);
intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE,getString(R.string.msg_sent));
startActivity(intent);

// 实现长按消失模式
<FrameLayout
xmlns:android="http://schemas.android.com/apk/res/android"
android:layout_height="match_parent"
android:layout_width="match_parent">

<!-- other views go here -->
<android.support.wearable.view.DismissOverlayView
android:id="@+id/dismiss_overlay"
android:layout_height="match_parent"
android:layout_width="match_parent"/>
<FrameLayout>

public class WearActivity extends Activity {

	private DismissOverlayView mDismissOverlay;
	private GestureDetector mDetector;

	public void onCreate(Bundle savedState) {
		super.onCreate(savedState);
		setContentView(R.layout.wear_activity);

		// Obtain the DismissOverlayView element
		mDismissOverlay = (DismissOverlayView) findViewById(R.id.dismiss_overlay);
		mDismissOverlay.setIntroText(R.string.long_press_intro);
		mDismissOverlay.showIntroIfNecessary();

		// Configure a gesture detector
		mDetector = new GestureDetector(this, new SimpleOnGestureListener() {
			public void onLongPress(MotionEvent ev) {
				mDismissOverlay.show();
			}
		});
	}

	// Capture long presses
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return mDetector.onTouchEvent(ev) || super.onTouchEvent(ev);
	}
}

// 数据层
GoogleApiCient mGoogleApiClient = new GoogleApiCient.Builder(this)
.addConnectionCallbacks(new ConnectionCallbacks){
	public void onConnected(Bundle connectionHint){
			// you can use the data layer api
	}

	public void onConnectionSuspended(int cause){

	}
})
.addOnConnectionFailedListener(new OnConnectionFailedListener(){
	public void onConnectionFailed(ConnectionResult result){

	}
})
.addApi(Wearable.API)
.build();

// 再调用实例的connect() 方法，系统就会在客户端已连接的时候回调onConnected() 函数。

// 一个DataItem定义了系统用于同步手机端和可穿戴设备间数据的接口，
// 1. Payload 一个字节数组，可以设置为任何你想要的数据，允许你将对象序列化和反序列化，但长度限制为100KB
// 2. Path 一个唯一的字符串，必须以 /开头

// 通常不用直接实现DataItem，而是应该这样：
// 1、创建一个PutDataRequest对象，定义一个path路径字符串来唯一识别这个item
// 2、调用setData()来设置payload
// 3、调用DataApi.putDataItem()来请求系统创建这个数据项。
// 4、当请求数据项的时候，系统会返回实现了DataItem接口的对象
// 如果不使用setData()，也推荐可以使用DataMap，来实现包含DataItem的一个类似Bundle的接口。

// 如果你想使用类似Android Bundle的形式组织DataItems，可以使用DataMap。
// 对象做好序列化和反序列化，将数据处理成键值对的形式。
// 使用DataMap的方式： 
// 1、创建一个PutDataMapRequest对象，设置DataItem路径path。
// 2、调用PutDataMapRequest.getDataMap() 方法来获取一个DataMap
// 3、调用DataMap的各种put...()方法来设置值。
// 4、调用PutDataMapRequest.asPutDataRequest()方法来获得一个PutDataRequest对象。
// 5、调用DataApi.putDataItem()方法来请求系统创建DataItem。 
// 如果手机和可穿戴设备断开了连接，那么数据会被缓存并在下次重建连接的时候同步数据。

PutDataMapRequest dataMap = PutDataMaprequest.create("/count");
dataMap.getDataMap().putInt(xxx,xxx);
PutDataRequest request = dataMap.asPutDataRequest();
PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi.putDataItem(mGoogleApiClient,request);

// 如果一方的数据层连接改变了DataItem，在另一方的连接可能希望得到数据改变的通知，
// 你可以通过实现一个DataItem事件监听器来做到
public void onDataChanged(DataEventBuffer dataEvents){
	for(DataEvent event: dataEvents){
		if(event.getType() == DataEvent.TYPE_DELETED){
			event.getDataItem().getUri();
		}else if(event.getType() == DataEvent.TYPE_CHANGED){
			event.getDataItem().getUri();
		}
	}
}

// 为了通过蓝牙传送来发送大量二进制数据，例如图片什么的，可以为DataItem附加一个Asset资源。

// 创建asset可以使用Asset类的一些create...()方法，
// 比如将图片作为字节流发送就可以调用createFromBytes() 方法：
private static Asset createAssetFromBitmap(Bitmap bitmap) {
	final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
	bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream);
	return Asset.createFromBytes(byteStream.toByteArray());
}

//当创建了asset之后，可以调用 putAsset()方法将它附加给DataItem，并且将DataItem放进数据存储中：
Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image);
Asset asset = createAssetFromBitmap(bitmap);
PutDataRequest request = PutDataRequest.create("/image");
request.putAsset("profileImage", asset);
Wearable.DataApi.putDataItem(mGoogleApiClient, request);

Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image);
Asset asset = createAssetFromBitmap(bitmap);
PutDataMapRequest dataMap = PutDataMapRequest.create("/image");
dataMap.getDataMap().putAsset("profileImage", asset)
PutDataRequest request = dataMap.asPutDataRequest();
PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi
.putDataItem(mGoogleApiClient, request);


// 实现一个asset变化的回调来读取它：
@Override
public void onDataChanged(DataEventBuffer dataEvents) {
	for (DataEvent event : dataEvents) {
		if (event.getType() == DataEvent.TYPE_CHANGED && event.getDataItem().getUri().getPath().equals("/image")) {
			DataMapItem dataMapItem = DataMapItem.fromDataItem(dataItem); 
			Asset profileAsset = dataMapItem.getDataMap().getAsset("profileImage");
			Bitmap bitmap = loadBitmapFromAsset(profileAsset);
		  // Do something with the bitmap
		}
	}
}

public Bitmap loadBitmapFromAsset(Asset asset) {
	if (asset == null) {
		throw new IllegalArgumentException("Asset must be non-null");
	}

	ConnectionResult result = mGoogleApiClient.blockingConnect(TIMEOUT_MS, TimeUnit.MILLISECONDS);
	if (!result.isSuccess()) {
		return null;
	}
	// convert asset into a file descriptor and block until it's ready
	InputStream assetInputStream = Wearable.DataApi.getFdForAsset(mGoogleApiClient, asset).await().getInputStream();
	mGoogleApiClient.disconnect();

	if (assetInputStream == null) {
		Log.w(TAG, "Requested an unknown Asset.");
		return null;
	}
	// decode the stream into a bitmap
	return BitmapFactory.decodeStream(assetInputStream);
}

// 并且消息可以附加两个东西： 1、一个任意的payload（可选） 2、唯一识别消息行为的路径
// 消息是个单向的沟通机制，即“fire-and-forget”（自主导）任务，例如发送一个消息给可穿戴设备来启动一个activity。
// 你也可以使用消息作为请求/响应模型，比如一方发送消息做些事，然后回发一个响应消息。

SendMessageResult result = Wearable.MessageApi.sendMessage(mGApiClient, node, START_ACTIVITY_PATH, null).await();
if(!result.getStatus().isSuccess()){
	// ...
}

// 有一种方式可以获取已连接设备节点的列表，可以给这些设备发送消息：
private Collection<String> getNodes() {
	HashSet<String> results = new HashSet<String>();
	NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();
	for (Node node : nodes.getNodes()) {
		results.add(node.getId());
	}
	return results;
}

// 为了得到接收消息的通知，可以实现一个消息事件的监听。
@Override
public void onMessageReceived(MessageEvent messageEvent) {
	if (messageEvent.getPath().equals(START_ACTIVITY_PATH)) {
		Intent startIntent = new Intent(this, MainActivity.class);
		startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(startIntent);
	}
}

// 如果你是在UI主线程调用，可以给pendingResult对象设置一个callback回调，来异步等待操作完成：
queuedpendingResult.setResultCallback(new ResutlCallbackDataItemResult>(){
	public void onResult(final DataItemResult result){
		if(result.getStatus().isSuccess()){
			result.getDataItem().getUri();
		}
	}
});

// 如果你在一个后台服务（例如WearableListenerService)的独立线程中运行，
// 可以直接调用pendingResult对象的await（）方法来同步等待操作结果并会返回一个DataItemResult对象。
DataItemResult result = pendingResult.await();
if(result.getStatus().isSuccess()) {
	Log.d(TAG, "Data item set: " + result.getDataItem().getUri());
}

// 监听数据层事件有两种方式可选：
// 1、创建一个service继承WearableListenerService.
// 2、创建一个activity实现DataApi.DataListener.

<service android:name=".DataLayerListenerService">
<intent-filter>
<action android:name="com.google.android.gms.wearable.BIND_LISTENER" />
</intent-filter>
</service>

public class DataLayerListenerService extends WearableListenerService {

	private static final String TAG = "DataLayerSample";
	private static final String START_ACTIVITY_PATH = "/start-activity";
	private static final String DATA_ITEM_RECEIVED_PATH = "/data-item-received";

	@Override
	public void onDataChanged(DataEventBuffer dataEvents) {
		
		if (Log.isLoggable(TAG, Log.DEBUG)) {
			Log.d(TAG, "onDataChanged: " + dataEvents);
		}

		final List events = FreezableUtils.freezeIterable(dataEvents);

		GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
		.addApi(Wearable.API)
		.build();

		ConnectionResult connectionResult = googleApiClient.blockingConnect(30, TimeUnit.SECONDS);

		if (!connectionResult.isSuccess()) {
			Log.e(TAG, "Failed to connect to GoogleApiClient.");
			return;
		}

		// Loop through the events and send a message
		// to the node that created the data item.
		for (DataEvent event : events) {
			Uri uri = event.getDataItem().getUri();

			// Get the node id from the host value of the URI
			String nodeId = uri.getHost();
			// Set the data of the message to be the bytes of the URI.
			byte[] payload = uri.toString().getBytes();

			// Send the RPC
			Wearable.MessageApi.sendMessage(googleApiClient, nodeId,DATA_ITEM_RECEIVED_PATH, payload);
		}
	}
}

// 为了解决这个，需要调用clearCallingIdentity()方法来重置进程身份，
// 并且在完成特殊操作后，调用restoreCallingIdentity方法来恢复进程身份。
long token = Binder.clearCallingIdentity();
try {
	performOperationRequiringPermissions();
} finally {
	Binder.restoreCallingIdentity(token);
}


// 创建Activity来监听数据事件的步骤：
// 1、实现某个想要的接口
// 2、在oncreate方法中，创建一个GoogleApiClient实例。
// 3、在onStart()方法中，调用GoogleApiClient实例的connect() 方法来连接Google Play services
// 4、当连接上了Google Play services之后，系统会回调onConnected()。
// 这个回调可以通过调用DataApi.addListener(), MessageApi.addListener(), 或 NodeApi.addListener() 来注册。
// 5、在onStop()方法中，调用 DataApi.removeListener(), MessageApi.removeListener(),或 NodeApi.removeListener()来取消注册的监听。
// 6、实现你想关心相关接口，比如onDataChanged(), onMessageReceived(), onPeerConnected(), 或onPeerDisconnected()。
public class MainActivity extends Activity implements DataApi.DataListener, ConnectionCallbacks, OnConnectionFailedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mGoogleApiClient = new GoogleApiClient.Builder(this)
		.addApi(Wearable.API)
		.addConnectionCallbacks(this)
		.addOnConnectionFailedListener(this)
		.build();
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (!mResolvingError) {
			mGoogleApiClient.connect();
		}
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		if (Log.isLoggable(TAG, Log.DEBUG)) {
			Log.d(TAG, "Connected to Google Api Service");
		}
		Wearable.DataApi.addListener(mGoogleApiClient, this);
	}

	@Override
	protected void onStop() {
		if (null != mGoogleApiClient && mGoogleApiClient.isConnected()) {
			Wearable.DataApi.removeListener(mGoogleApiClient, this);
			mGoogleApiClient.disconnect();
		}
		super.onStop();
	}

	@Override
	public void onDataChanged(DataEventBuffer dataEvents) {
		for (DataEvent event : dataEvents) {
			if (event.getType() == DataEvent.TYPE_DELETED) {
				Log.d(TAG, "DataItem deleted: " + event.getDataItem().getUri());
			} else if (event.getType() == DataEvent.TYPE_CHANGED) {
				Log.d(TAG, "DataItem changed: " + event.getDataItem().getUri());
			}
		}
	}
}

// 为Notification添加wearable特性
// 如果你需要为Notification添加一些可穿戴的特性设置，比如制定额外的内容页，或者让用户通过语音输入一些文字，
// 那么你可以使用 NotificationCompat.WearableExtender来制定这些设置。
// 请使用如下的API： 
// 创建一个WearableExtender的实例，设置可穿戴独有的Notification的选项。
// 创建一个NotificationCompat.Builder的实例，就像本课程先前所说的，设置需要的Notification属性。
// 执行Notification的extend())方法，参数是WearableExtender实例。
// Create a WearableExtender to add functionality for wearables
NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender().setHintHideIcon(true);

// Create a NotificationCompat.Builder to build a standard notification
// then extend it with the WearableExtender
Notification notif = new NotificationCompat.Builder(mContext)
.setContentTitle("New mail from " + sender)
.setContentText(subject)
.setSmallIcon(R.drawable.new_mail);
.extend(wearableExtender)
.build();
// 如果你需要获取可穿戴特性的内容，可以使用相应的get方法，
// 该例子通过调用getHintHideIcon()去获取当前Notification是否隐藏了icon
NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender(notif);
boolean hintHideIcon = wearableExtender.getHintHideIcon();

// 为了创建一个多页的Notification，你需要： 
// 通过NotificationCompat.Builder创建主Notification（首页），这个首页也是你想要呈现在手持设备上的。
// 通过NotificationCompat.Builder为wear添加更多的页面.
// 通过addPage()方法为主Notification应用这些添加的页面，或者通过addPage()添加一个Collection的多个页面。



// scrolling and hide toolbar
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
android:layout_width="match_parent"
android:layout_height="match_parent">

<android.support.v7.widget.RecyclerView
android:id="@+id/recyclerView"
android:layout_width="match_parent"
android:layout_height="match_parent"/>

<android.support.v7.widget.Toolbar
android:id="@+id/toolbar"
android:layout_width="match_parent"
android:layout_height="?attr/actionBarSize"
android:background="?attr/colorPrimary"/>

<ImageButton
android:id="@+id/fabButton"
android:layout_width="56dp"
android:layout_height="56dp"
android:layout_gravity="bottom|right"
android:layout_marginBottom="16dp"
android:layout_marginRight="16dp"
android:background="@drawable/fab_background"
android:src="@drawable/ic_favorite_outline_white_24dp"
android:contentDescription="@null"/>

</FrameLayout>

// put them in a framelayout because toolbar needs to be overlayed on RecyclerView.
public class MainActivity extends ActionBarActivity {

	private Toolbar mToolbar;
	private ImageButton mFabButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initToolbar();
		mFabButton = (ImageButton) findViewById(R.id.fabButton);
		initRecyclerView();
	}

	private void initToolbar() {
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(mToolbar);
		setTitle(getString(R.string.app_name));
		mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
	}

	private void initRecyclerView() {
		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		RecyclerAdapter recyclerAdapter = new RecyclerAdapter(createItemList());
		recyclerView.setAdapter(recyclerAdapter);
		recyclerView.setOnScrollListener( new HidingScrollListener(){

			public void onHide(){
				hideViews();
			}

			public void onShow(){
				showViews();
			}
		});

		private void hideViews(){
			mToolbar.animate().translationY(-mToolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2) );
			FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mFabButton.getLayoutParams();
			int fabBottomMargin = lp.bottomMargin;
			mFabButton.animate().translationY(mFabButton.getHeight()+fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
		}

		private void showViews() {
			mToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
			mFabButton.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
		}
	}


}

public class RecyclerItemViewHolder extends RecyclerView.ViewHolder{

	private final TextView mItemTextView;

	public RecyclerItemViewHolder(final View parent, TextView itemTextView){
		super(parent);
		mItemTextView = itemTextView;
	}

	public static RecyclerItemViewHolder newInstance(View parent){
		TextView itemTextView = (TextView) parent.findViewById(R.id.xxx);
		return new RecyclerItemViewHolder(parent, itemTextView);
	}

	public void setItemText(CharSequence text){
		mItemTextView.setText(text);
	}
}

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

	private static final int TYPE_HEADER = 2;
	private static final int TYPE_ITEM = 1;

	private List<String> mItemlist;

	public RecyclerAdapter(List<String> itemList){
		mItemlist = itemList;
	}

	// public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
	// 	final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.xxx,false);
	// 	return RecyclerItemViewHolder.newInstance(view);
	// }

	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
		Context context = parent.getContext();
		if( viewType == TYPE_ITEM){
			final View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
			return RecyclerItemViewHolder.newInstance(view);
		}else if(viewType == TYPE_HEADER){
			final View view = LayoutInflater.from(context).inflate(R.layout.recycler_header, parent, false);
			return new RecyclerHeaderViewHolder(view);
		}
		throw new RuntimeException("xxxx");
	}

	// public void onBindViewHolder(RecyclerView.ViewHolder viewholder, int position){
	// 	RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewholder;
	// 	String itemText = mItemlist.get(position);
	// 	holder.setItemText(itemText);
	// }
	public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position){
		if(!isPositionHeader(positioin)){
			RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewholder;
			String itemText = mItemlist.get(position - 1);
			holder.setItemText(itemText);
		}
	}

	// public int getItemCount(){
	// 	return mItemlist == null ? 0 : mItemlist.size();
	// }

	public int getBasicItemCount(){
		return mItemlist == null ? 0 : mItemlist.size();
	}

	public int getItemCount(){
		return getBasicItemCount() + 1;
	}

	public int getItemViewType(int position){
		if(isPositionHeader(position)){
			return TYPE_HEADER;
		}
		return TYPE_ITEM;
	}

	private boolean isPositionHeader(int position){
		return position == 0;
	}

}

public abstract class HidingScrollListener extends RecyclerView.OnScrollListenr{

	private static final int HIDE_THRESHOLD = 20;
	private int scrolledDistance = 0;
	private boolean controlsVisible = true;


	// public void onScrolled(RecyclerView recyclerView, int dx, int dy){
	// 	super.onScrolled(recyclerView, dx, dy);

	// 	if( scrolledDistance > HIDE_THRESHOLD && controlsVisible){
	// 		onHide();
	// 		controlsVisible = false;
	// 		scrolledDistance = 0;
	// 	}else if( scrolledDistance < -HIDE_THRESHOLD && !controlsVisible){
	// 		onShow();
	// 		controlsVisible = true;
	// 		scrolledDistance = 0;
	// 	}

	// 	if( (controlsVisible && dy > 0) || (!controlsVisible && dy < 0)){
	// 		scrolledDistance += dy;
	// 	}
	// }
	@Override
	public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
		super.onScrolled(recyclerView, dx, dy);
		
		int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
  //show views if first item is first visible position and views are hidden
		if (firstVisibleItem == 0) {
			if(!controlsVisible) {
				onShow();
				controlsVisible = true;
			}
		} else {
			if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
				onHide();
				controlsVisible = false;
				scrolledDistance = 0;
			} else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
				onShow();
				controlsVisible = true;
				scrolledDistance = 0;
			}
		}
		
		if((controlsVisible && dy>0) || (!controlsVisible && dy<0)) {
			scrolledDistance += dy;
		}
	}

	public abstract void onHide();
	public abstract void onShow();
}


// toolbar 隐藏
private RecyclerView.OnScrollListener recyclerScrollListener =
	new RecyclerView.OnScrollListener(){

		public boolean flag;

		public void onScrolled(RecyclerView recyclerView, int dx, int dy){
			super.onScrolled(recyclerView, dx, dy);

			if(dy > 10){
				if( !flag ){
					showToolbar();
					flag = true;
				}
			}else if( dy < 10){
				if(flag){
					hideToolbar();
					flag  = false;
				}
			}
		}
	}

private void showToolbar(){

}

private void hideToolbar(){
	
}

// 适配器模式 接口适配，类对象适配
// 可以重用一个现有的类，以满足客户端的需要。
// 当客户端通过接口表达其需求时，通常可以创建一个实现了该接口的新类，同时使该类继承自现有类。
// 这种方式为类的适配，能够将客户端的调用转换为对现有类方法的调用。
// 当客户端没有指定它所需要的接口时，可能需要创建一个新的客户端的之类，它将使用现有类的实例。
// 这种方式通过创建一个对象适配器，将客户端的调用指向现有类的实例。
// 如果我们不需要（或许不能）重写客户端可能调用的方法时，这种方式可能存在一定的危险性。

interface RocketSim{
	double getMass();
	double getThrust();
	setSimTime(Double double);
}

public class OozinozRocket extends PhysicalRocket implements RocketSim{

	private double time;

	public OozinozRocket(double burnArea, double burnRate, double fuelMass, double totalMass){
		super(burnArea,burnRate,fuelMass,totalMass);
	}

	public double getMass(){

	}

	public double getThrust(){

	}

	public void setSimTime(double time){
		this.time = time;
	}
}

public class OozinozSkyrocket extends Skyrocket{

	private PhysicalRocket rocket;

	public OozinozSkyrocket(PhysicalRocket r){
		super(r.getMass(0),r.getThrust(0),r.getBurnTime());
		rocket = r;
	}

	public double getMass(){
		return rocket.getMass(simTime);
	}

	public double getThrust(){
		return rocket.getThrust(simTime);
	}
}

// 合成模式
/*
抽象构件角色 interface Component
树叶构件角色 leaf
树枝构件角色 Composite
*/

// 安全式的合成模式
// 要求管理聚集的方法只出现在树枝构件类中
public interface Component{
	Composite getCompsite();

	void sampleOperation();
}

public class Composite implements Component{

	private Vector componentVector = new Vector();

	public Composite getCompsite(){
		return this;
	}

	public void sampleOperation(){

	}

	public void add(Component component){

	}

	public void remove(Component component){

	}

	public Enumeration components(){
		return componentVector.elements();
	}
}

public class Leaf implments Component{

	public void sampleOperation(){

	}

	public Composite getCompsite(){

	}
}

// 透明式的合成模式的结构
// 均符合固定的接口
public interface Component{
	void sampleOperation();

	Composite getComposite();

	void add(Component component);

	void remove(Compoent component);

	Enumeration components();
}

public class Composite implements Component{

	private Vector compoentVector = new Vector();

	public Composite getCompsite(){
		return this;
	}

	public void sampleOperation(){

	}

	public void add(Component component){

	}

	public void remove(Component component){

	}

	public Enumeration components(){
		return compoentVector.elements();
	}
}

public class Leaf implements Component{

	public void sampleOperation(){

	}

	// 等其他需要实现的接口
}

// 装饰模式
// 以客户端透明的方式扩展对象的功能，是继承关系的一个替代方案
// 动态的给一个对象附加上更多的责任，客户端不知道装饰前后有什么不同

/*
抽象构件Component 给出一个抽象接口，以规范准备接收附加责任的对象
具体构件Cocrete Component： 定义一个将要接收附加责任的类。
装饰角色 Decorator ： 持有一个构件Component对象的实例，并定义一个与抽象构件接口一致的接口
具体装饰 Concrete Decorator：负责给构件对象贴上附加的责任
*/

public interface Component{
	void sampleOperation();
}

public class Decorator implements Component{

	private Component component;

	public Decorator(Component component){
		this.Component = component;
	}

	public Decorator(){

	}

	public void sampleOperation(){
		component.sampleOperation();
	}
}

public class ConcreteComponent implements component{

	public ConcreteComponent(){

	}

	public void sampleOperation(){

	}
}

public class ConcreteDecorator extends Decorator{

	public void sampleOperation();
}

// 代理模式
// 是对象的结构模式
// 给某一个对象提供一个代理对象，并由代理对象控制对原对象的引用。

// 按照使用目的来划分：
// 远程remote代理：
// 虚拟Virtual代理：
// 。。。

//  系统提供了向真实主题传递客户端请求的控制。
// 代理主题可以在向真实主题传递客户端请求之前执行特定的操作，并决定是否将请求传递给真实主题；
// 代理主题可以在向真实主题传递客户端请求之后执行另外一种操作等。

abstract public class Subject{
	abstract public void request();
}

public class RealSubject extends Subject{
	public RealSubject(){

	}

	public void request(){
		// ...
	}
}

public class ProxySubject extends Subject{

	private RealSubject realSubject;

	public ProxySubject(){

	}

	public void request(){
		preRequest();
		if(RealSubject == null){
			realSubject = new RealSubject();
		}

		realSubject.reuqest();
		postRequest();
	}

	private void preRequest(){

	}

	private void postRequest(){

	}
}


// 调用代理主题
Subject subject = new ProxySubject();
subject.request();

// 享元模式 Flyweight Pattern
// 单纯享元模式和复合享元模式
// 对象的结构模式，以共享的方式高效地支持大量的细粒度对象
// 区分内蕴状态 Internal State 和 外蕴状态 External State
// 一个内蕴状态是存储在享元对象内部的，并且是不会随环境改变而有所不同的。
// 因此，一个享元可以具有内蕴状态并可以共享。

// 一个外蕴状态是随环境改变而改变的、不可以共享的状态。
// 对象的外蕴状态必须由客户端保存，并在享元对象被创建之后，在需要的时候再传入的享元对象内部。
// 内外蕴是相互独立的。


// state 参数代表了享元对象的外蕴状态
abstract public class Flyweight{
	abstract public void operation(String state);
}

public class ConcreteFlyweight extends Flyweight{

	private Character intrinsicState = null;

	public ConcreteFlyweight(Character state){
		this.intrinsicState = state;
	}

	public void operation(String state){
		// ...
	}
}

// 客户端不可以直接将具体享元实例化，而必须通过一个工厂对象，
public class FlyweightFactory{

	private HashMap flies = new HashMap();
	private Flyweight lnkFlyweight;

	public FlyweightFactory(){}

	public Flyweight factory(Character state){
		if(flies.cotainsKey(state)){
			return (Flyweight)files.get(state);
		}else{
			Flyweight fly = new ConcreteFlyweight(state);
			flies.put(state,fly);
			return fly;
		}
	}

	public void checkFlyweight(){
		Flyweight fly;
		int i = 0;
		// ...
		for(Iterator it = flies.entrySet().iterator();it.hasNext();){
			Map.Entry e = (Map.Entry)it.next();
		}
	}
}

// 使用享元模式
FlyweightFactory factory = new FlyweightFactory();
Flyweight fly = factory.factory(new Character('a'));
fly.operation("First call");
fly = factory.factory(new Character('b'));
fly.operateMethod1("second call");
fly = factory.factory(new Character('a'));
fly.operation("third call");

// 复合享元模式
abstract public class Flyweight{
	abstract public void operation(String state);
}

public class ConcreteFlyweight extends Flyweight{

	private Character intrinsicState = null;

	public ConcreteFlyweight(Character state){
		this.intrinsicState = state;
	}

	public void operation(String state){
		// ...
	}
}

public class ConcreteCompositeFlyweight extends Flyweight{
	private HashMap flies = new HashMap(10);

	private Flyweight flyweight;

	public ConcreteCompositeFlyweight(){

	}

	public void add(Character key,Flyweight fly){
		flies.put(key, fly);
	}

	public void operation(String extrinsicState){
		Flyweight fly = null;
		for(Iterator it = flies.entrySte().iterator();it.hasNext();){
			Map.Entry e = (Map.Entry)it.next();
			fly = (Flyweight)e.getValue();
			fly.operation(extrinsicState);
		}
	}
}

public class FlyweightFactory{

	private HashMap flies = new HashMap();

	public FlyweightFactory(){}

	public Flyweight factory(String compositeState){
		ConcreteCompositeFlyweight compositeFly = new ConcreteCompositeFlyweight();
		int length = compositeState.length();
		Character state = null;
		for(int i=0; i<length; i++){
			state = new Character(compositeState.charAt(i));
			compositeFly.add(state,this.factory(state));
		}

		return compositeFly;
	}

	public Flyweight factory(Character state){
		if(flies.containsKey(state)){
			return (Flyweight)flies.get(state);
		}else{
			Flyweight fly = new ConcreteFlyweight(state);
			flies.put(state,fly);
			return fly;
		}
	}

	public void checkFlyweight(){
		Flyweight fly;
		int i = 0;
		// for
	}
}

Flyweight fly = factory.factory("aba");
fly.operation("Composite call");

// 门面模式 Facade
// 对象的结构模式，外部与一个子系统的通信必循通过一个统一的门面Facade对象进行
// 提供一个高层次的接口，使得子系统更易于使用


// 桥梁模式


// 不变模式


// wear 可穿戴
// 指定可穿戴设备独有的actions
Intent actionIntent = new Intent(this, ActioniActivity.class);
PendingIntent actionPendingIntent = PendingIntent.getActivity(this,0,actionIntent,PendingIntent.FLAG_UPDATE_CURRENT);
NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.drawable.ic_action,getString(R.string.label,actionPendingIntent)).build();

Notification notification = new NotificationCompat.Builder(mContext)
	.setSmallIcon(R.drawable.ic_message)
	.setContentTitle(getString(R.string.title))
	.setContentText(getString(R.string.content))
	.extend(new WearableExtender().addAction(action))
	.build();

// 大图
BigTextStyle bigStyle = new NotificationCompat.BigTextStyle();
bigStyle.bigText(eventDescription);
NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
	.setSmallIcon(R.drawable.ic_event)
	.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.notif_background))
	.setContentTitle(eventTitle)
	.setContentText(eventLocation)
	.setContentIntent(viewPendingIntent)
	.addAction(R.drawable.ic_map,getString(R.string.map),mapPendingIntent)
	.setStyle(bigStyle);



// 可穿戴特性
NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender().setHintHideIcon(true);

Notification notif = new NotificationCompat.Builder(mContext)
	.setContentTitle("xxx")
	.setContentText(subject)
	.setSmallIcon(R.drawable.new_mail)
	.extend(wearableExtender)
	.build();

NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender(notif);
boolean hintHideIcon = wearableExtender.getHintHideIcon();

// 提交通知
NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);
notificationManager.notify(notificationId,notif);

// 让用户口述一个回复或者通过RemoteInput预先设定好文本信息

//定义语音输入
private static final String EXTRA_VOICE_REPLY = "xxxx";
String replyLabel = getResources().getString(R.string.reply_label);
RemoteInput remoteInput = new RemoteInput.Builder(EXTRA_VOICE_REPLY).setLabel(replyLabel).build();

// 你还可以提供多达5条的文本反馈，这样用户可以直接进行选择实现快速回复。
RemoteInput remoteInput = new RemoteInput.Builder(EXTRA_VOICE_REPLY)
	.setLabel(replyLabel)
	.setChoices(replyChoices)
	.build();

// 添加语音输入作为Notification的action
Intent replyIntent = new Intent(this, ReplyActivity.class);
PendingIntent replyPendingIntent = PendingIntent.getActivity(this,0,replyIntent,PendingIntent.FLAG_UPDATE_CURRENT);

NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.drawable.ic_reply_icon,getString(R.string.label,replyPendingIntent))
	.addRemoteInput(remoteInput).build();

Notification notification = new NotificationCompat.Buidler(mContext)
	.setSmallIcon(R.drawable.ic_message)
	.setContentTitle(getString(R.string.title))
	.setContentText(getString(R.string.content))
	.extend(new WearableExtender().addAction(action))
	.build();

NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);
notificationManager.notify(notificationId, notification);

private CharSequence getMessageText(Intent intent){
	Bundle remoteInput = RemoteInput.getResultFromIntent(intent);
	if(remoteInput != null){
		return remoteInput.getCharSequence(EXTRA_VOICE_REPLY);
	}
	return null;
}

// 为Notification添加显示页面
NotificationCompat.Builder notificaitionBuidler = new NotificationCompat.Builder(this)
	.setSmallIcon(xxx)
	.setContentTitle(xxx);
	.setContentText("xxx");
	.setContentText("xxx")
	.setContentIntent(viewPendingIntent);

BigTextStyle secondPageStyle = new NotificationCompat.BigTextStyle();
secondPageStyle.setBigContentTitle("xxx").bigText("xxxxx");

Notification secondePageNotification = new NotificationCompat.Builder(this).setStyle(secondPageStyle).build();

Notification twoPageNotification = new WearableExtender()
	.addPage(secondePageNotification)
	.extend(notificationBuilder)
	.build();

notificationManager = NotificationManagerCompat.from(this);
notificationManager.notify(notificationId,twoPageNotification);


// 以Stack的方式显示Notifications
final static String GROUP_KEY_EMAILS = "group_key_emails";
Notification notif = new NotificationCompat.Builder(mContext)
	.setContentTitle()
	.setContentText()
	.setSmallIcon()
	.setGroup(GROUP_KEY_EMAILS)
	.build();

NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
notificationManager.notify(notificationId, notif);

Notification notif2 = new NotificationCompat.Builder(mContext)
	.setContentTitle()
	.setContentText()
	.setSmallIcon()
	.setGroup(GROUP_KEY_EMAILS)
	.build();

notificationManager.notify(notificationId, notif2);
// 最近的Notification会被放置在最顶端。你可以通过setSortKey()来修改Notification的排顺序

// 添加概括式Notification


// 通常来说，你应该尽可能的把运行操作搬到手持设备上，然后发送操作结果到可穿戴设备。

// 如果标准的notification风格无法满足你的需求(例如NotificationCompat.BigTextStyle 或者 NotificationCompat.InboxStyle)，
// 你可以使用activity，显示一个自定义的布局来达到目的。
// 在可穿戴设备上你只可以创建并处理自定义的notification，同时系统无法为这些notification同步到手持设备上。
// 当在可穿戴设备上创建自定义的notification时，你可以使用API Level 20上标准的APIs，不需要使用Support Library

// 1 创建布局并设置这个布局为需要显示的activity的content view；
public void onCreate(Bundle bundle){
	//...
	setContentView(R.layout.xxx);
}

// 2 为了使得activity能够显示在可穿戴设备上，需要在manifest中定义必须的属性
<activity android:name="com.example.MyDisplayActivity"
  android:exported="true"
  android:allowEmbedded="true"
  android:taskAffinity=""
  android:theme="@android:style/Theme.DeviceDefault.Light" />

// 3 创建PendingIntent
Intent notificationIntent = new Intent(this, NotificationActivity.class);
PendingIntent notificationPendingIntent = PendingIntent.getActivity(this,0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);

// 4. 创建Notification并执行setDisplayIntent())方法，参数是前面创建的PendingIntent。
// 当用户查看这个notification时，系统使用这个pendingIntent来启动activity

// 5 触发notification使用notify()的方法。
// 当用户往上滑动notification时，将会看到为这个notification准备的自定义的activity。


// 穿戴提供了2种类型的语音操作：
// + 系统提供的
// 这些语音指令都是基于任务的，并且内置在Wear的平台内。
// 你在activity中过滤你想要接收的指令。例如包含"Take a note" 或者 "Set an alarm"的指令。

// 应用提供的
// 这些语音指令都是基于应用的，你需要像声明一个Launcher Icon一样定义这些指令。
// 用户通过说"Start XXX"来使用那些语音指令，然后会启动你指定启动的activity。/**/

// 对于"Take a note"的指令，定义一个MyNoteActivity来接收这个指令:

<activity android:name="MyNoteActivity">
      <intent-filter>
          <action android:name="android.intent.action.SEND" />
          <category android:name="com.google.android.voicesearch.SELF_NOTE" />
      </intent-filter>
  </activity>

// 在"Start"指令的后面需要指定的文字, 这个文字需要注册在activity的label属性上。
//例如，下面的设置能够识别"Start MyRunningApp"的语音指令并启动StartRunActivity.

<application>
  <activity android:name="StartRunActivity" android:label="MyRunningApp">
      <intent-filter>
          <action android:name="android.intent.action.MAIN" />
          <category android:name="android.intent.category.LAUNCHER" />
      </intent-filter>
  </activity>
</application>

// startActivityForResult()使用ACTION_RECOGNIZE_SPEECH启动系统语音识别应用。
// 在onActivityResult()中处理返回的结果：


private static final int SPEECH_REQUEST_CODE = 0;

private void displaySpeechRecognizer(){
	Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
	intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
	startActivityForResult(intent,SPEECH_REQUEST_CODE);
}

protected void onActivityResult(int requestCode, int resultCode, Intent data){
	if(requestCode == SPEECH_REQUEST && resultCode == RESULT_OK){
		List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
		String spokenText = results.get(0);
	}

	super.onActivityResult(requestCode, resultCode, data);
}

// 可穿戴数据层 the wearable data layer API
// 是 Google Play service 的一部分
// 为你的手持与可穿戴应用提供一个交流通道。
// 此API包括一系列的数据对象，其可由系统通过网络和能告知你应用数据层重要事件的监听器发送并同步

// Data items
// 数据元提供了手持设备与可穿戴设备间的自动同步的数据存储

// Messsages
// MessageApi 类可以发送自动跟踪命令消息，

// Asset
// 资源对象是为了发送如图像这样的二进制数据。将资源附加到数据元，系统会自动负责传递，并通过缓存大的资源来避免重复传送

// WearableListenerService(for services)
// 扩展的 WearableListenerService能够监听一个service中重要的数据层事件。
// 系统控制其生命周期，并当需要发送数据元或消息时，将其与service绑定，否则解除绑定。

// DataListener（for foreground activities)
// 在一个前台activity中实现DataListener能够监听重要的数据通道事件。
// 只有当用户频繁地使用应用时，用此代替WearableListenerService来监听改变。
// 不能试着打开底层sockkets来创建通信通道

// 调用数据层API，需创建一个GoogleApiClient实例，所有Google Play services APIs 的主要入口点。
// GoogleApiClient 提供一个易于创建客户端实例的builder。最简单的GoogleApiClient如下：
GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
	.addConnectionCallbacks(new ConnectionCallbacks(){
		public void onConnected(Bundle connectionHint){
			//...
		}

		public void onConnectionSuspended(int cause){
			// ...
		}	
	})
	.addOnConnectionFailedListener(new OnConnectionFailedListener(){
		public void onConnectionFailed(ConnectionResult result){
			// ...
		}

	})
	// 请求只访问Wearable API
	.addApi(Wearable.API)
	.build();

// 在你使用数据层API之前，通过调用connect())方法进行连接，如 Start a Connection中所述。
// 当系统为你的客户端调用了onConnected()) 方法，你就可以使用数据层API了。

// DataItem 是指系统用于同步手持设备与可穿戴设备间数据的接口。
// 一个DataItem通常包括以下几点：
// Pyload： 一个字节数组，可以设置为任何你想要的数据，允许你将对象序列化和反序列化，但长度限制为100KB
// Path： 唯一且以斜线开头的string /path/to/data

// 通常不直接实现DataItem，而是：
// 1. 创建一个PutdataRequest对象，指明一个string path 以唯一确定该 item。
// 2. 调用setData()方法设置Pyload。
// 3. 调用DataApi.putDataItem()方法，请求系统创建数据元。
// 4 .当请求的时候，系统会返回正确实现DataItem接口的对象。
// 然而，我们建议使用Data Map来显示装在一个易用的类似Bundle接口中的数据元，而用不是setData()来处理原始字节。


// 使用DataMap类，将数据元处理为 Android Bundle的形式，
// 因此对象的序列化和反序列化就会完成，你就可以以 key-value 对的形式操纵数据。

// 1. 创建一个 PutDataMapRequest对象，设置数据元的path。
// 2. 调用PutDataMapRequest.getDataMap())获取一个你可以使用的data map 对象。
// 3. 使用put...()方法，如：putString()),为data map设置数据。
// 4. 调用PutDataMapRequest.asPutDataRequest())获得PutDataRequest对象。
// 5. 调用 DataApi.putDataItem()) 请求系统创建数据元
// Note: 如果手机和可穿戴设备没有连接，数据会缓冲并在重新建立连接时同步。

public class MainActivity extends Activity implements 
	DataApi.DataListener, 
	GoogleApiClient.ConnectionCallbacks,
	GoogleApiClient.OnConnectionFailedListener{

	private static final String COUNT_KEY = "xxx.com.key.count";

	private GoogleApiClient mGoogleApiClient;
	private int count = 0;

	private void increaseCounter(){
		PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/count");
		putDataMapReq.getDataMap().putInt(COUNT_KEY, count++);
		PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
		PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq);
	}

	// ...
}

public class MainActivity extends Activity implements
	DataApi.DataListener,
	GoogleApiClient.ConnectionCallbacks,
	GoogleApiClient.OnConnectionFailedListener{

	private static final String COUNT_KEY = "com.example.key.count";

    private GoogleApiClient mGoogleApiClient;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
        	.addApi(Wearable.API)
        	.addConnectionCallbacks(this)
        	.addOnConnectionFailedListener(this)
        	.build();
    }

    @Override
    protected void onResume() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    public void onConnected(Bundle bundle){
    	Wearable.DataApi.addListener(mGoogleApiClient, this);
    }

     @Override
    protected void onPause() {
    	super.onPause();
    	Wearable.DataApi.removeListener(mGoogleApiClient, this);
    	mGoogleApiClient.disconnect();
    }

    public void onDataChanged(DataEventBuffer dataEvents){
    	for(DataEvent event: dataEvents){
    		if( event.getType() == DataEvent.TYPE_CHANGED){
    			DataItem item = event.getDataItem();
    			if(item.getUri().getPath().compareTo("/count") == 0){
    				DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
    				updateCount(dataMap.getInt(COUNT_KEY));
    			}
    		}else if(event.getType() == DataEvent.TYPE_DELETED){
    			// dataitem 删除了
    		}
    	}
    }

     // 我们的更新 count 的方法
    private void updateCount(int c) { ... }

    // ...
}