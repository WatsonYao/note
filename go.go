package main

import "fmt"

// const (
// 	a, b = 1, 2
// 	c    = iota
// 	d    = iota
// )

func main() {
	fmt.Printf("hello, Watson\n")
	// fmt.Println(a)
	// fmt.Println(b)
	// fmt.Println(c)
	// fmt.Println(d)

	// a := 1
	// var p *int = &a

	// for i := 0; i < 3; i++ {
	// 	fmt.Println(i)
	// }
	// fmt.Println("Over")

	// a := func() {
	// 	fmt.Println("Func A")
	// }
	// a()

	// f := closure(10)
	// f(1)

	B()
}

// 闭包 返回一个匿名函数
// func closure(x int) func(int) int {
// 	return func(y int) int {
// 		return x + y
// 	}
// }

func B() {
	defer func() {
		if err := recover(); err != nil {
			fmt.Println("recover ->")
		}
	}()
	panic("xxx")

}

type USB interface {
	Name() string
	Connect()
}

type PhoneConnecter struct {
	name string
}

func (pc PhoneConnecter) Name() string {
	return pc.name
}

func (pc PhoneConnecter) Connect() {
	fmt.Println("Connect:", pc.name)
}

func main2() {
	// 有缓存 异步
	// 无缓存 同步阻塞的
	c := make(chan bool, 1)
	go func() {
		fmt.Println("xxx")
		c <- true
		close(c)
	}()
	//<-c

	for v := range c {
		fmt.Println(v)
	}
}

// 工作任务 同步包
func main3(){
	runtime.GOMAXPROCS(runtime.NumCPU()))
	
	wg := sync.WaitGroup{}
	wg.Add(10)

	for i:=10 ; i<10; i++{
		go Go(&wg,i)
	}

	wg.Wait()
}

func Go(wg *sync.WaitGroup, index int){
	a := 1
	for i:=0; i<10000000; i++{
		a += i
	}
	fmt.Println(index, a)

	wg.Done();
}

// 处理多个channel select
func main4(){
	c1,c2 := make(chan int),make(chan string)
	o := make(chan bool)

	go func(){
		for{
			select{
				case v,ok := <- c1:
					if !ok{
						o<-true
						break;
					}
					fmt.Println("c1",v)
				case v,ok := <- c2:
					if !ok{
						o<- true
						break;
					}
					fmt.Println("c2",v) 
			}
		}
	}()

	c1 <- 1
	c2 <- "hi"
	c1 <- 3
	c2 <- "hello"

	close(c1)
	close(c2)

	<- o
}