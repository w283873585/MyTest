package jun.learn.tools.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jun.learn.tools.network.bioServer.StringPacket;

public class Client {
	
	public static void main(String[] args) {
		connect(1);
	}
	
	public static void connect(int size) {
		for (int i = 0; i < size; i++) {
			new Thread() {
				public void run() {
					doConnect();
				}
			}.start();;
		}
	}
	
	private static void doConnect() {
		Socket socket = new Socket();
		try {
			socket.connect(new InetSocketAddress(12020));
			DataPacket<String> packet = new StringPacket();
			
			/**
			 * 独立线程去读取内容， 
			 * 以解包的方式读取， 每次只读取一个包
			 */
			ExecutorService service = Executors.newFixedThreadPool(1);
			service.execute(new Runnable() {
				@Override
				public void run() {
					try {
						while (true) {
							String msg = packet.read(socket.getInputStream());
							if (Util.isShutdown(msg)) {
								System.out.println("客户端准备关闭..");
								socket.close();
								service.shutdown();
								System.out.println("客户端已经关闭..");
								break;
							}
							System.out.println(msg);
						}
					} catch (IOException e) { 
						e.printStackTrace();
					}
				}
			});
			
			/**
			 * 循环发布1000个消息
			 */
			for (int i = 0; i < 10; i++)
				packet.write(socket.getOutputStream(), "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
//				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
				+ "这首五言绝句写的是在寂静的月夜思念家乡的感受.从“疑”到“望”到“思”形象地揭示了诗人的内心活动,鲜明地勾勒出一幅月夜思乡图.诗歌的语言清新朴素,明白如话；表达上随口吟出,一气呵成.但构思上却是曲折深细的.诗歌的内容容易理解,但诗意却体味不尽.诗的前两句“床前明月光,疑是地上霜”,是写诗人在作客他乡的特定环境中一刹那间所产生的错觉.月色如霜的秋夜,一个独处他乡的人,心头泛起阵阵思念故乡的波澜.“疑是地上霜”中的“疑”字,生动地表达了诗人如梦如醒,迷离恍惚中将照射在床前的清冷月光误作铺在地面的浓霜.而“霜”字用得更妙,既形容了月光的皎洁,又表达了季节的寒冷,还烘托出诗人飘泊他乡的孤寂凄凉之情,诗的后两句“举头望明月,低头思故乡”,则通过动作神态的刻画,深化思乡之情.“望”字照应了前句的“疑”字,表明诗人已从迷朦转为清醒,他翘首凝望着月亮,不禁想起,此刻他的故乡也正处在这轮明月的照耀下.于是自然引出了“低头思故乡”的结句.“低头”这一动作描画出诗人完全处于沉思之中.而“思”字又给读者留下丰富的想象：那家乡的父老兄弟、亲朋好友,那家乡的一山一水、一草一木,那逝去的年华与往事……无不在思念之中.一个“思”字所包涵的内容实在太丰富了."
				);
			
			Scanner sc = new Scanner(System.in);
			while (true) {
				String msg = sc.nextLine();
				packet.write(socket.getOutputStream(), msg);
				if (Util.isShutdown(msg)) {
					sc.close();
					break;
				}
			}
			
			// 通知服务器，我要关闭连接了。
			// Util.sendShutdownCommand(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
