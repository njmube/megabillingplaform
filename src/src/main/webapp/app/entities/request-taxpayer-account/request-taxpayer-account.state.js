(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('request-taxpayer-account', {
            parent: 'entity',
            url: '/request-taxpayer-account?page&sort&search',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'megabillingplatformApp.request_taxpayer_account.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/request-taxpayer-account/request-taxpayer-accounts.html',
                    controller: 'Request_taxpayer_accountController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('request_taxpayer_account');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('request-taxpayer-account-detail', {
            parent: 'entity',
            url: '/request-taxpayer-account/{id}',
            data: {
                authorities: [],
                pageTitle: 'megabillingplatformApp.request_taxpayer_account.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/request-taxpayer-account/request-taxpayer-account-detail.html',
                    controller: 'Request_taxpayer_accountDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('request_taxpayer_account');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Request_taxpayer_account', function($stateParams, Request_taxpayer_account) {
                    return Request_taxpayer_account.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('request-taxpayer-account.new', {
            parent: 'request-taxpayer-account',
            url: '/new',
            data: {
                authorities: [],
                pageTitle: 'megabillingplatformApp.request_taxpayer_account.home.title'
            },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/request-taxpayer-account/request-taxpayer-account-new.html',
                        controller: 'Request_taxpayer_accountNewController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('request_taxpayer_account');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
        })
        .state('request-taxpayer-account.afilitated', {
                parent: 'request-taxpayer-account',
                url: '/afilitated',
                data: {
                    authorities: [],
                    pageTitle: 'megabillingplatformApp.request_taxpayer_account.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/request-taxpayer-account/resquest-taxpayer-account-afilitated.html',
                        controller: 'Request_taxpayer_accountAfilitatedController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('request_taxpayer_account');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
        .state('request-taxpayer-account.edit', {
            parent: 'request-taxpayer-account',
            url: '/{id}/edit',
            data: {
                authorities: []
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/request-taxpayer-account/request-taxpayer-account-dialog.html',
                    controller: 'Request_taxpayer_accountDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Request_taxpayer_account', function(Request_taxpayer_account) {
                            return Request_taxpayer_account.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('request-taxpayer-account', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('request-taxpayer-account.delete', {
            parent: 'request-taxpayer-account',
            url: '/{id}/delete',
            data: {
                authorities: []
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/request-taxpayer-account/request-taxpayer-account-delete-dialog.html',
                    controller: 'Request_taxpayer_accountDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Request_taxpayer_account', function(Request_taxpayer_account) {
                            return Request_taxpayer_account.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('request-taxpayer-account', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
