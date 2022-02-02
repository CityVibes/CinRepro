
#import <Foundation/Foundation.h>
#import "CinteropRepro.h"

@implementation FAPI {

}

-(instancetype)initSystem {
    self = [super init];
    return self;
}


-(void)dealloc {
    
}

-(id)subscribe:(NSString*)resource
       handler:(Subscriber)handler {
    Subscriber dispatchedHandler = ^(NSString *updatedResource) {

    };
    return dispatchedHandler;
}


-(id)subscribeSafe:(NSString*)resource
       handler:(Subscriber)handler {
    // not returned
    Subscriber dispatchedHandler = ^(NSString *updatedResource) {

    };
    
    ReturnType *rtype = [[ReturnType alloc] initSystem];
    // not a typedef, return ReturnType
    return rtype;
}


@end


@implementation ReturnType {

}

-(instancetype)initSystem {
    self = [super init];
    return self;
}


-(void)dealloc {
    
}

@end
