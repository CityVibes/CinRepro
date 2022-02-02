#pragma once

#import <Foundation/Foundation.h>

#define API_EXPORT __attribute__((visibility("default")))


typedef void(^Subscriber)(NSString * _Nonnull resource);

API_EXPORT
@interface FAPI : NSObject

-(instancetype _Nullable)initSystem;


-(id _Nullable)subscribe:(NSString* _Nonnull)resource
                 handler:(Subscriber _Nonnull)handler;

-(id _Nullable)subscribeSafe:(NSString* _Nonnull)resource
                 handler:(Subscriber _Nonnull)handler;


@end

@interface ReturnType : NSObject

-(instancetype _Nullable)initSystem;

@end
