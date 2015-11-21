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

