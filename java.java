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
