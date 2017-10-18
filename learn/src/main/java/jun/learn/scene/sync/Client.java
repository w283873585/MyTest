package jun.learn.scene.sync;

public class Client {
	
	/**
	 * DataProvider provider = getDataProvider();
	 * Synchronizer synchronizer = getSynchronizer(provider);
	 * synchronizer.sync();
	 * 
	 * 
	 * 
	 * DataProvider: {
	 * 		getData: function() {},
	 * 
	 * 		// or getData: function(name, type) {}
	 * }
	 * 
	 * Synchronizer: {
	 * 		sync: function() {
	 * 			provider.getData();
	 * 
	 * 		},
	 * }
	 */
	
	/**
     * 1. 拉取数据 	-> 数据来源方式	-> db, webservice
     * 2. 数据验证, 
     * 		正常数据
     * 		异常数据处理, 
     * 		策略预验证来源数据
     * 		
     * 3. 逻辑处理
     * 		// 同步日志
     * 		// TODO 细致一点, 列举出所有需要同步的数据
     * 		1. 判断数据是否更新
     * 
     * 
     * 数据提供方：
     * 		全部数据
     * 		已修改数据
     * 
     * 数据接收方：
     * 		数据对比
     * 		逻辑处理
     * 
     * 
     *	利用更新时间做同步
     *	记录上次同步时间, 
     */
	
	public static void main(String[] args) {
		Synchronizer sync = new Synchronizer();
		sync.sync("a", 2);
	}
}
