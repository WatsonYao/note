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

