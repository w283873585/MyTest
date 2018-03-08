package jun.learn.scene.verify;

public class Client {
	/** 1. 
	 * 			Class<?> reqClass = null;
	 * 			HttpServletRequest request = null;
	 * 			List<Contstraint> constraints = getContstraints(reqClass);
	 * 			RequiredVerifyData requiredVerifyData = getRequiredVerifyData(request);
	 * 			for (Constraint constraint : constraints) {
	 * 				if (!contstraint.matches(requiredVerifyData)) {
	 * 					// failed 	
	 * 				}
	 * 			}
	 * 		
	 * 		Constraint {
	 * 			boolean matches(RequiredVerifyData data)
	 * 		}
	 * 		
	 * 		RequiredVerifyData{
	 * 			Object get(String key);
	 * 		}
	 * 
	 *  2.
	 *  
	 *  	Class<?> reqClass = null;
	 * 		HttpServletRequest request = null;
	 * 		List<Contstraint> constraints = getContstraints(reqClass);
	 * 		RequiredVerifyData requiredVerifyData = getRequiredVerifyData(request);
	 * 		for (Constraint constraint : constraints) {
	 * 			if (!requiredVerifyData.satisfy(constraint)) {
	 * 				// failed 	
	 * 			}
	 * 		}
	 * 
	 * 		RequiredVerifyData{
	 * 			boolean satisfy(Constraint constraint) {
	 * 				
	 * 			}
	 * 		}
	 *  	
	 *  	Constraint {
	 * 			boolean matches(Object value)
	 * 		}
	 * 
	 * 
	 * 	1. 收集约束
	 *  2. 收集数据
	 *  3. 验证
	 */
}
