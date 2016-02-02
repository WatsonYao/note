data class Customer(val name: String, val email:String)

// 函数默认值
fun foo(a:Int = 0; b: String = ""){
 // ...
}

// string 内联
pritln("name $name")

// 类型判断
when(x){
  is Foo -> ...
  is Bar -> ...
  else -> ...
}

// 遍历
for( (k,v) in map){
  println("$k -> $v")
}

// 区间
for (i in 1..100)

// 只读链表
val list = listOf("a","b","c")

// 只读表
val map = mapOf("a" to 1, "b" to 2, "c" to 3)

// 创建单例
oject Resource{
  val name = "Name"
}

// if not null 缩写
val files = File("Test").listFiles();
println(files?.size)


http://www.datamation.com/open-source/top-open-source-android-apps-chatting-pics-audio-and-more.html

定义函数
fun sum(a: Int , b: Int) : Int{
  return a + b
}

有一个表达式的函数体 以及 一个推断类型的返回值
fun sum(a:Int, b Int) = a + b

要想函数在模块外面可见
就必须有一个确定的返回值
public fun sum(a: Int, b: Int): Int = a + ｂ

函数返回一个没有意义的值
fun printSum(a:Int,b:Int):Uint{
  print(a+b)
}

声明静态变量（只读）
val a: Int = 1
val b = 1
val c: Int
c = 1

可变的变量
var x = 5
x += 1

字符串模板
${args[0]}

条件表达式
if(a < b)
  return a
else
  return b

把 if 当表达式
fun max(a:Int, b:Int) = if(a<b) a else b

当空值可能出现时应明确指出可空
fun parseInt(str: String): Int?{
  // ...
}

val x = parseInt(args[0])
val y = parseInt(args[1])

if( x != null && y != null){
  print( x * y)
}

或者这样
if(x == null){
  print("")
  return
}

if(y == null){
  print("")
  return
}

print(x * y)

使用值检查自动转换
使用 is 操作符检查一个表达式是否是某个类型的实例
如果对一个不可变的局部变量属性检查是否是某种特定类型，就没有必要明确转换
fun getStringLength(obj: Any): Int?{
  if( obj is string){
    return obj.length
  }

  return null
}

或者这样
fun getStringLength(obj: Any): Int?{
  if( obj is string)
    return obj.length

  // obj 将会在这个分支中自动转换为 String 类型
  return null
}

甚至可以这样
fun getStringLength(obj: Any): Int?{
  if(obj is String && obj.length>0)
    return obj.Length
  return null
}

使用循环
fun main(args: Array<String>){
  for (arg in args)
    print(arg)
}

或者
for(i in args.indices)
  print(args[i])

使用 while / when 等
when(obj){
  1 -> print()
  "hello" -> print()
  else -> print()
}

使用 ranges
if ( x in 1..y-1)
  print("OK")

if ( x !in 0..array.lastIndex)
  print("Out")

使用集合
for (name in names)
  print(name)

if (text in names)
  print("Yes")

names filter {
  it.startWith("A")
}
sortBy {
  it
}
map {
  it.toUpperCase()
}

forEach {
  print(it)
}

数据类
data class Customer(val name: String, val email: String)


声明局部 final 变量
val a = foo()

函数默认值
fun foo(a:Int = 0, b: String = ""){...}

过滤list
val positives = list.filter{ x -> x>0 }
或者更短
val positives = list.filter{ it > 0 }

遍历 map/list
for ((k,v) in map){
  print("$k -> $v")
}

使用 ranges
for(i in 1..100){ ... }
for(i in 2..10){ ... }
 

只读 list
val list = listOf("a", "b", "c")

只读 map
val map = mapOf("a" to 1,"b" to 2, "c" to 3)

获取 map 中的值
println(map["key"])
map["key"] = value

Lazy property
val p: String by Delegates.lazy{

}

扩展函数（给现有类增添新函数）
fun String.spaceToCamelCase(){ ... }
"xxxx to xxxx".spaceToCamelCase()

创建单例模式
object Resource{
  val name = "Name"
}


if not null shorthand
val files = File("Test").listFiles()
println(files?.size)

if not null and else shorthand
val files = File("test").listFiles()
println(files?.size ?: "empty")

如果为空执行某操作
val data = ...
val email = data["email"] ?: throw IllegalStateException("xxx")

如果不为空执行某操作
val data = ...
data?.let{
  ...// 如果不为空 执行该语句
}

数值方面，Kotlin 不存在隐式转换数值的宽度，
并且在字面上有一些小小的不同

Kotlin 提供了如下内建数值类型
Double 64
Float 32
Long 64
Int 32
Short 16
Byte 8

短类型不是长类型的 子类型。
短类型是不会隐式转换为 长类型的。
必须显式转换才能把 Byte 赋值给 Int
val i: Int = b.toInt()

字符类型 用 Char 表示。
不能直接当做数值来使用。
fun check(c: Char){
  if(c == 1){ // Error 类型不匹配
    // ...
  }
}

字符是单引号包起来的
可以显式的把它转换为 Int

if( c !in '0'..'9')
  throw xxx
return c.toInt() - '0'.toInt() // 显示转换为数值类型

布尔值
只有 true 或者 false

Array 类
有get 和 set 方法
class Array<T> private (){
  fun size(): Int
  fun get(index: Int): T
  fun set(index: Int,value: T) Uint
  fun iterator(): Iterator<T>
}

可以给 arrayOf 传递每一项的值来创建 Array
用 arrayOfNulls 创建一个指定大小的空 Array

或者通过指定 Array 大小并提供一个迭代器
val asc = Array(5, {i -> (i*i).toString() })

val x: IntArray = intArray(1,2,3)
x[0] = x[1] + x[2]


类 可以有一个主构造函数以及多个二级构造函数
主构造函数是类头的一部分
跟在类名后面（可以有可选的参数）
class Person constructor(firstName: String){
}

如果构造函数没有注解 或 可见性说明，
则 constructor 关键字可以省略
class Person(firstName: String){
}

主构造函数不能包含任意代码。
初始化代码可以放在以init 做前缀的初始化块内
class Customer(name: String){
  init{
    logger.info("")
  }
}

主构造函数的参数可以用在初始化块内，
也可以用在类的属性初始化声明处：
class Customer(name: String){
  val customerKey = name.toUpperCase()
}

声明属性并在主构造函数中初始化它们如下：
class Person(val firstName:String, val lastName:String, var age: Int){
}

如果构造函数有注解 或 可见性 声明，
则 constructor 关键字是不可少的，并且注解应该在前

class Customer public inject constructor (name: String) {...}

二级构造函数
前缀是 constructor

class Person{
  constructor(parent: Person){
    parent.children.add(this)
  }
}

如果类有主构造函数，
每个二级构造函数都要 或直接或间接
通过另一个二级构造函数代理主构造函数
在同一个类中代理另一个构造函数使用 this 关键字
class Person(val name:String){
  constructor (name: String, parent: Person): this(name){
    parent.children.add(this)
  }
}

如果一个非抽象类没有声明构造函数（主或二级）
它会产生一个没有参数的构造函数 public
如果不想有公共的构造函数，必须声明一个空的主构造函数
class DontCreateMe private constructor(){
}

类可以包含 构造函数和初始化代码块、函数、属性、内部类、对象声明

所有类都有共同的父类 Any
声明一个明确的父类，需要在类头后面再加父类：
open class Base(p: Int)
class Dervied(p: Int): Base(p)

如果类有主构造函数，则基类可以而且必须在主构造函数中立即初始化。
如果类没有主构造函数，则必须在每一个构造函数中用 super 关键字初始化基类
或者在代理另一个构造函数做这件事。注意在这种情形中
不同的二级构造函数可以调用基类不同的构造方法
class MyView : View{
  constructor(ctx:Context):super(ctx){
  }
  constructor(ctx:Context，attrs: AttributeSet): super(ctx,attrs){
  }
}

open 注解允许别的类继承这个类
默认情况下所有类都是final的

需要把可以重写的成员都明确注解出来，并且重写他们
open class Base{
  open fun v(){}
  fun nv(){}
}

class Derived(): Base(){
  override fun v() {}
}

如果不想重写就加 final
open class AnotherDerived():Base(){
  final override fun v(){}
}

如果一个类从它的直接父类继承了同一个成员的多个实现
那么它必须重写这个成员并且提供自己的实现
（或许只是直接用了继承来的实现）
为表示使用父类中提供的方法我们用 super<Base> 表示
open class A{
  open fun f(){ print("A") }
  fun a(){ print("a") }
}

interface B{
  fun f(){ print("B") }
  fun b(){ print("b") }
}

class C() : A(),B{
  override fun f(){
    super<A>.f()
    super<B>.f()
  }
}
f() 有两个实现，因此我们在 C 中必须重写 f() 并且提供自己的实现来消除歧义

抽象类
不用给抽象类 或 函数添加 open 注解，它默认是带着的

没有静态方法，建议只用包级别的函数
如果你要写一个IE么有实例类就可以调用的方法，
但需要访问到类内部
可以把它写成它所在类的一个成员

更高效的方法是，你可以在你的类中声明一个伴随对象，
这样就可以当做静态方法调用

属性和字段
var isEmpty:Boolean
  get() = this.size == 0

var stringRepresentation: String
  get() = this.toString()
  set(value){
    setDataFormString(value)
  }

如果需要改变一个访问器的可见性 或者给它添加注解
但又不想改变默认的实现
定义一个不带函数体的访问器
var setVisibilite:String = "ab" // 非空类型必须初始化
  private set
var setterWithAnnotation: Any?
  @Inject set // 用 Inject 注解 setter

类不可以有字段
然而使用自定义的访问器时需要备用字段
var counter = 0
  set(value){
    if (value >= 0)
      field = value
  }
field 关键字只能用于属性的访问器

备用属性
想要做一些事情但不适合这种 隐含备用字段 方案
private var _table: Map<String, Int>? = null
public val table:Map<String, Int>
  get(){
    if(_table == null)
      _table = HashMap()
      return _table ?: throw AssertionError()
  }
和 java 很相似，可以避免函数访问私有属性而破坏它的结构

编译时常量
这种属性需要同时满足以下条件：
定义在函数外或者是一个对象的成员以String 或 基本类型进行初始化
没有自定义 getter
这种属性可以当做注解使用
const val SUBSYSTEM_DEPRECATED: String = "xxxx"
@Deprected(SUBSYSTEM_DEPRECATED) fun foo(){ ... }

那些被定义为拥有非空类型的属性，
都需要在构造器中初始化。
在单元测试中，属性应该通过依赖注入进行初始化
或者通过一个setup方法进行初始化

上述条件下，你不能在构造器中提供一个非空的初始化语句
但仍然需要在访问这个属性的时候 避免非空检查

public class MyTest{
  lateinit var subject: TestSubject

  @Setup fun setup(){
    subject = TestSubject()
  }

  ＠Test fun test(){
    subject.method()
  }
}

这个修饰符只能够被用在类的 var 类型的可变属性定义中，
不能用在构造方法中。并且属性不能有自定义的getter 和 setter访问器
这个属性的类型必须是非空的。
同样也不能为一个基本类型。

代理属性
从备用属性中读或者写
另一方面，自定义的getter 和 setter 可以实现属性的任何操作
有些像 lazy values
根据给定的关键字从map中读出，读取数据库
通知一个监听者 等等

接口
可以包含抽象方法，以及方法的实现
接口不能保存状态，可以有属性但必须是抽象的
interface MyInterface{
  fun bar()
  fun foo(){
    // 函数体是可选的
  }
}

接口的实现
一个类或对象可以实现一个或多个接口
class Child:MyInterface{
  fun bar (){
    // 函数体
  }
}

接口中的属性
Interface MyInterface{
  val property: Int // 抽象属性
  fun foo(){
    print(property)
  }
}

class Child: MyInterface{
  override val property: Int = 29
}

当我们在父类中声明了许多类型
有可能出现一个方法的多种实现

interface A{
  fun foo){ print("A") }
  fun bar()
}

interface B{
  fun foo(){print("B")}
  fun bar(){print("bar"}
}

class C : A{
  override fun bar() { print("bar")}
}

class D: A,B{
  override fun foo(){
    super<A>.foo()
    super<B>.foo()
  }
}

可见性 修饰词
类 对象 接口 构造函数 属性 setter方法都可以有可见性修饰词
getter方法作为属性时都是可见的

private 只在声明的范围和同一个模块的子范围可见
protected 只可用在类或接口成员上） 和privte 很像，
但在子类中可见；
internal 默认使用 在同一个模块中都可见
public 在任何地方均可见

函数、属性、类、对象、接口
可以在top-level 声明
package foo
fun baz() {}
class bar {}

类 和 接口：
当在类中声明时

扩展函数 和 扩展属性
为了声明一个扩展函数，
需要函数名使用接收者类型作为前缀
fun MutableList<Int>.swap(x:Int, y:Int){
  val temp = this[x]
  this[x] = this[y]
  this[y] = temp
}

val l = mutableListOf(1,2,3)
l.swap(0,2)

通用化
fun <T> MutableList<T>.swap(x:Int, y:Int){
  val tmp = this[x]
  this[x] = this[y]
  this[y] = tmp
}

扩展实际上并没有修改它所扩展的类。
定义一个扩展，并没有在类中插入一个新的成员，
只是让这个类的实例对象能偶通过 ． 调用新的函数
需要强调的是扩展函数是静态分发的
它们并不是接受者类型的虚拟方法。
如果有同名同参的成员函数和扩展函数，
调用的时候必须会使用成员函数
class C{
  fun foo() {Print("member")}
}

fun C.foo{
  println("extension")
}
上述只会调用member

注意扩展可以使用空接受者进行定义
并在函数体内检查 this == null
这样就可以调用toString 而不进行空指针检查
空指针检查延后到扩展函数中完成
fun Any?.toString():String{
  if(this == null) return "null"
  return toString()
}

扩展属性
val <T> List<T>.lastIndex: Int
  get() = size -1
由于扩展并不会真正给类添加成员属性
因此也没有办法让扩展属性拥有一个备份字段
这也是为什么初始化函数是不允许扩展属性
扩展属性只能够通过直接提供 get/set 方法来进行定义
val Foo.bar = 1 // error

伴随对象扩展
如果一个对象定义了伴随对象
也可以给伴随对象添加扩展函数或扩展属性
class MyClass{
  companion object{}
}

fun Myclass.Companion.foo(){
}

调用 Myclass.foo()

数据类
data class User(val name:String, val age: Int)
数据对象，编译器会根据主构造函数自动给所有属性添加方法等

如果在类中明确声明或从基类继承了这些方法
编译器就不会自动生成了


泛型

变动
java 类型系统有 通配符类型，但kotlin没有，
替代它的是：
声明变化 和 类型预测
declararion-site variance and type projections

嵌套类
class Ouer{
  private val bar: Int = 1
  class Nested{
    fun foo() = 2
  }
}

val demo = Outer.Nested().foo()

内部类
class Outer{
  private val bar: Int = 1
  inner class Inner{
    fun foo() = bar
  }
}

val demo = Outer().Inner().foo()

枚举类

匿名类

匿名内部类
对象表达式
希望用对象表达式和声明来解决这个问题
window.addMouseListener(object: MouseAdapter(){
  override fun mouseClicked(e: MouseEvent){
    // ...
  }
})

如果父类有构造函数，则必须传递相应的构造函数
多个父类可以用逗号隔开，跟在冒号后面
open class A(x:Int){
  public open val y:Int = x
}

interface B { ... }

val ab = object: A(1),B{
  override val y = 14
}

有时候我们只是需要一个没有父类的对象：
val adHoc = object{
  var x: Int = 0
  var y: Int = 0
}

print(adHoc.x + adHoc.y)

和java的匿名内部类一样
对象表达式可以访问闭合范围内的变量
fun countClicks(windows: JComponent){
  var clickCount = 0
  var enterCount = 0
  window.addMouseListener(object: MouseAdapter){
    override fun mouseClicked(e: MouseEvent){
      clickCount++
    }
    override fun mouseEntered(e: MoseEvent){
      enterCount++
    }
  })
}

对象声明
单例模式
object DataProviderManager{
  fun registerDataProvider(provide: Dataprovider){
    // ...
  }
  val allDataproviders : Collection<DataProvider>
    get() = // ...
}

伴随对象
class MyClass{
  companion object Factory{
    fun create(): MyClass = MyClass()
  }
}

val instance = MyClass.create()
class MyClass{
  companion object{
     // ...
  }
}

对象声明是lazily 初始化的，我们只能访问一次
对象表达式在我们使用的地方立即初始化并执行的

代理
代理给实现继承提供了很好的替代方式
interface Base{
  fun print()
}

class BaseImpl(val x: Int) : Base{
  override fun print(){ print(x) }
}

class Derived(b: Base) : Base by b

fun main(){
  val b = BaseImpl(10)
  Derived(b).print()
}

在 Derived 的父类类表中的条款意味着 b 将会
存储在 Derived 对象中并且编译器会生 Base 的所方法并转给 b

代理属性
延迟属性、观察属性、

class Example{
  var p: String by Delegate()
}
这个属的 get 和 set 就代理给它了
属性代理不需要任何接口的实现
但必须提供get方法 如果是变量还要提供set方法
class Delegate{
  fun get(thisRef: Any?, prop: PropertyMetadata): String{
    return "$thisRef,thank you for delegating
  }

  fun set(thisRef: Any?,prop: PropertyMatadata,value: String){
    // ...
  }
}
当我们从p 也就是 Delegate 的代理中读东西时
会调用 Delegate 的 get 函数
因此第一个参数是我们 p 中读取的
第二参数是 p 自己的一描述

val e = Example()
println(e.p)
同样，当我们分配p时 set函数会调用
前两参数一样的，第三个持分配的值
e.p = "new"

代理属性的要求
只读属性 val，代理必须提供一个名字为get的方法并接如参数
接受者-必须是相同的，或者是属性拥有者的子类型
元数据-必须是 PropertyMetadata 或 其子类型

这个函数必须返回同样的类型作为属性

可变属性var，代理必须提供一个名为 set 的函数并接受如下参数
接受者-同上
元数据-同上
新值-必须和属性类型一致 或是它的子类型

标准代理
kotlin.properties.Delegates 对象是标准提供的一个工厂方法
并提供了很有用的代理
















