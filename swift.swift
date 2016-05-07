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

// 通过扩展添加的实例方法也可以修改该实例本身
// 结构体和枚举类型中修改 self 或其属性的方法必须将 实例方法标注为mutating，正如来自原始实现的方法一样。

extension Double{
    mutating func square(){
        let pi = 3.14
        self = pi * self * self
    }
}

// 扩展可以向一个已有类型添加新下标
extension Int{
    subscript( multtable: Int) -> Int{
        var no1 = 1
        while multtable > 0{
            nol *= 10
            -- multtable
        }
        return (self / no1) % 10
    }
}

// 扩展可以向已有的类、结构体和枚举添加新的嵌套类型：

// 协议用于指定特定的实例属性或类属性，而不用指定存储属性或计算型属性。
// 此外还必须指明是只读的还是可读可写的。
// 协议中通常用 var 来声明变量属性，在类型声明后加上 ｛set get｝来表示属性是可读可写的，只读用 ｛get｝来表示

// 你可以在遵循该协议的类中实现构造器，并指定其为类的指定构造器或者便利构造器。在这两种情况下，你都必须给构造器实现上"required"修饰符：
// 你可以在协议的继承列表中,通过添加class关键字,限制协议只能适配到类（class）类型。

func show(celebrator: protocol<Stname, Stage>) {
    print("\(celebrator.name) is \(celebrator.age) years old")
}

//你可以使用is和as操作符来检查是否遵循某一协议或强制转化为某一类型。
//is操作符用来检查实例是否遵循了某个协议。
//    as?返回一个可选值，当实例遵循协议时，返回该协议类型;否则返回nil。
//as用以强制向下转型，如果强转失败，会引起运行时错误。

