##################  Spring Security########################
## UserDetailsServices   ##
用户信息获取逻辑
## UserDetails  ##
处理用户逻辑
## PasswordEncoder  ##
处理密码加密解密


##  httpSecurity    ##
formlogin
:loginPage()  登录的页面或者Url
:loginProcessingUrl("") 自定义登录验证 过滤器执行

自定义loginPage的时候注意一下判断引发判断的是html还是json
从请求缓存来拿 RequestCache 当前请求会缓存到httpsessionRequestCache中

自定义登录成功处理
ObjectMapper了解一下
实现AuthenticationSuccessHandler
默认的成功处理器 SavedRequestAwareAuthenticationSuccessHandler

自定义登录失败处理
实现AuthenticationFailHandler
默认处理器 SimpleUrlAuthenticationFailureHandler


##  Session 工具类 ##
SessionStrategy

##  记住我 ##
RememberMeService-> TokenRepository
RememberMeAuthenticationFilter

##  Spring Security基于表单的密码验证请求  ##
UsernamePasswordAuthenticationFilter
        |
        |UsernamePasswordAuthenticationToken(未认证)
        |
AuthenticationManager
        |
DaoAuthenticationProvider
        |
UserDetailsServices
        |
UserDetails
        |
Authentication(已认证)
        |
SecurityContext
        |
SecurityContextHolder
        |
SecurityContextPersistenceFilter 

###############  OAUTH服务提供商 Provider #####################
主要实现认证服务器&&资源服务器
##  认证服务器  Authentication Server ##
4种授权模式 || 自定义的认证
    |
Token生成的存储

##  资源服务器 Resource Server  ##
资源(rest 服务)
验证令牌
Spring Security过滤器链OAUTH2AuthenticationProcessingFilter


##############  资源所有者 Resource Owner  ###################


################### 第三方应用 Client  ########################

#################   Spring Social   #########################
## 过滤器 ##
SocialAuthenticationFilter
##  验证提供    ##
AbstractOAuth2ServiceProvider

##  默认验证流程  ##
        ServiceProvider
    (AbstractOAuth2ServiceProvider)

    OAuth2Operations 认证流程
    (OAuth2Template)

        API (读取用户信息)
(AbstractOAuth2ApiBinding)

##  获取用户信息  ##
    Connection
  (OAuth2Connection)
  
    ConnectionFactory
  (OAuth2ConnectionFactory)
  (ServiceProvider)
  (ApiAdapter)
  #   存储用户信息  #
  UsersConnectionRepository
  (JDBCUsersConnectionRepository)

########## oauth/token  ###########
过滤器順序執行
WebAsyncManagerIntegrationFilter
    |
SecurityContextPersistenceFilter
    |
HeaderWriterFilter
    |
LogoutFilter
    |
ClientCredentialsTokenEndpointFilter
    |
ClientIdHeaderInterceptor
    |
BasicAuthenticationFilter
    |
RequestCacheAwareFilter
    |
SecurityContextHolderAwareRequestFilter
    |
AnonymousAuthenticationFilter
    |
SessionManagementFilter
    |
ExceptionTranslationFilter
    |
FilterSecurityInterceptor



#############  异常  ###############
##      InvalidTokenException    ##



############## 权限的访问地址  ###############
##  TokenEndpoint                         ##
##  CheckTokenEndpoint                    ##
##  AuthorizationEndpoint                 ##
##  TokenKeyEndpoint                      ##
##  WhitelabelApprovalEndpoint            ##
##  WhitelabelErrorEndpoint               ##