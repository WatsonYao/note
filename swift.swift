class TV{
	var keyCount = "12"
	var keyName = "Power"

	func openTV()->String{
		return "Success"
	}

	init(name:String){
		self.name = name
	}

	deinit{
		name=""
	}
}

class MiTV:TV{
	
	override var name:String{
		get{
			return self.name
		}
		set{

		}
	}
}

var miTV = TV()
miTV.keyName

struct TV{
	var keyName = "xxx"
	var keyNumber = 12
	func getKeyNumber()->Int{
		return keyNumber
	}
}

// 值类型
var myTV = TV(keyName:"xxx",keyNumber:12)

// 类和结构体
// 类可以继承，可以析构，可以多重引用 ,结构不可


//协议，协议是可以继承的
protocol Action{
	var myName:String{
	get set
	}

	var myAge:Int{
	get
	}

	func getMyName()->String
}

class InvokeAction:Action{
	
}

class XXXX:基类,协议{
	
}

// 协议合并 同时实现协议
func hello(s:protocol<Action,ChildAction>){
	
}


// 泛型
func swapValue<T>(inout a:T, inout b:T){
	let temp = a
	a = b
	b = temp
}

var a = "a"
var b = "b"
swapValue(&a, &b)

class Vclass<T>{
	
	func getName(s:T){

	}

}

var vv = Vclass<Int>()

// 类，枚举，结构 都可以采用协议

let nextItem = UIBarButtonItem(title:"xxx",style:.Plain,target:self,action:"nextPage")
self.navigationItem.rightBarButtonItem = nextItem;

class RootViewController:UIViewController,FontSizeChangeDelegate}{
	
	func fontSizeDidChange(controller:SecondViewController, fontSize:Int){
		//...
		let font = UIFont.systemFontOfSize(Float(fontSize))
		myLabel!.font = font;
	}
}

func nextPage(){
	//...
	let svc = SecondViewController();
	svc.delegate = self;
	self.navigationController.pushViewController(svc,animated:true);
}

var b = UIButton.buttonWithType(.System) as UIButton
var frame = CGRect(xywh)
b.frame = frame
b.setTitle("xxx",fotState:.Normal)
b.addTarget(self,action:"clickMe:",forControlEvents:.TouchUpInside)
self.view.addSubview(b)
func clickMe(sender:UIButton){
	self.navigationController.popViewControllerAnimated(true);
}

var fontSize:Int = 20;

func clickMe(sender:UIButton){
	fontSize++;
	if(delegate){
		delegate?.fontSizeDidChange(self,fontSize:fontSize)
	}
}

// 定义一个协议
protocol FontSizeChangeDelegate:NSObjectProtocol{
	func fontSizeDidChange(controller:SecondViewController, fontSize:Int)
}

class SecondViewController:UIVIewController{
	var delegate:FontSizeChangeDelegate?
}

//TabBar
class AppDelegate:...{

	func application(application:UIApplication, didFinishLaunching...) -> Bool{
		let vc1:UIViewController = FirstViewController();
		let nav1 = UINavigationController(rootViewController:vc1)
		let image1 = UIImage(named:"xxx.png")
		nav1.tabBarItem = UITabBarItem(title:"xx",image:image1,tag:1)

		// vc2 vc3

		let arr = [nav1,nav2,nav3]
		let tabBarControoller = UITabBarController()
		tabBarController.viewControllers = arr
		self.window!.rootViewController = tabBarController;
	}
}

// TableView
class RootViewController : UIViewController,UITableViewDelegate,UITableViewDataSource
var dataArr = NSMutableArray()
var rect:CGRect = self.view.bounds
tableView = UITableView(frame:rect, style:.Plain)
tableView!.delegate = self
tableView!.dataSource = self;
self.view.addSubview(tableView)

func tableView(tableView:UITableView!, numberOfRowInSection section:Int) -> Int{
	return dataArr.count;
}

func tableView(tableView:UITableView!, cellForRowAtIndexPath indexPath:NSIndexPath!) -> UITableViewCell!{
		let cellid = "my cell id"
		var cell = tableView.dequeueReusableCellWithIndetifier(cellid) as? UITableViewCell;
		if(cell == nil){
			cell = UITableViewCell(style:.Default, reuseIdentifier:cellid)
		}

		var s = dataArr.objectAtIndex(indexPath.row) as? String
		cell!.textLabel.text = s
		return cell
}

func tableView(tableView:UITableView!, didSelectRowAtIndexPath indexPath:NSIndexPath!){
	
}
