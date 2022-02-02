#import "ViewController.h"
#import "CinteropShared/CinteropShared.h"
#import "CinteropRepro/CinteropRepro.h"

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];

    // ----------------Running fine----------------
    FAPI *api = [[FAPI alloc] init];
    
    [api subscribe:@"Any route" handler:^(NSString * _Nonnull resource) {
        
    }];
    
    // -----------------Crash------------
    
    CinteropSharedCinteropUseCase *useCase = [[CinteropSharedCinteropUseCase alloc] init];

    // Crash
    [useCase subscribeAndReturnError:nil];
    // No crash
    [useCase subscribeSafeAndReturnError:nil];
}


@end
