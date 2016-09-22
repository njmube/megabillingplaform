(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('taxpayer-account', {
            parent: 'entity',
            url: '/taxpayer-account?page&sort&search',
            data: {
                authorities: [],
                pageTitle: 'megabillingplatformApp.taxpayer_account.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/taxpayer-account/taxpayer-accounts.html',
                    controller: 'Taxpayer_accountController',
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
                    $translatePartialLoader.addPart('taxpayer_account');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('taxpayer-account-detail', {
            parent: 'entity',
            url: '/taxpayer-account/{id}',
            data: {
                authorities: [],
                pageTitle: 'megabillingplatformApp.taxpayer_account.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/taxpayer-account/taxpayer-account-detail.html',
                    controller: 'Taxpayer_accountDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('taxpayer_account');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Taxpayer_account', function($stateParams, Taxpayer_account) {
                    return Taxpayer_account.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('taxpayer-account-general', {
                parent: 'entity',
                url: '/taxpayer-account-general/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'megabillingplatformApp.taxpayer_account.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/taxpayer-account/taxpayer-account-general.html',
                        controller: 'Taxpayer_accountGeneralController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('taxpayer_account');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Taxpayer_account', function($stateParams, Taxpayer_account) {
                        return Taxpayer_account.get({id : $stateParams.id}).$promise;
                    }]
                }
            })
        .state('taxpayer-account-users', {
                parent: 'entity',
                url: '/taxpayer-account-users/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'megabillingplatformApp.taxpayer_account.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/taxpayer-account/taxpayer-account-users.html',
                        controller: 'Taxpayer_accountUsersController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('taxpayer_account');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Taxpayer_account', function($stateParams, Taxpayer_account) {
                        return Taxpayer_account.get({id : $stateParams.id}).$promise;
                    }]
                }
            })
        .state('taxpayer-account-index', {
                parent: 'entity',
                url: '/taxpayer-account-index/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'megabillingplatformApp.taxpayer_account.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/taxpayer-account/taxpayer-account-index.html',
                        controller: 'Taxpayer_accountIndexController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('taxpayer_account');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Taxpayer_account', function($stateParams, Taxpayer_account) {
                        return Taxpayer_account.get({id : $stateParams.id}).$promise;
                    }]
                }
            })
        .state('taxpayer-account.new', {
            parent: 'taxpayer-account',
            url: '/new',
            data: {
                authorities: []
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taxpayer-account/taxpayer-account-dialog.html',
                    controller: 'Taxpayer_accountDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                rfc: null,
                                bussines_name: null,
                                email: null,
                                fax: null,
                                phone1: null,
                                phone2: null,
                                accuracy: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('taxpayer-account', null, { reload: true });
                }, function() {
                    $state.go('taxpayer-account');
                });
            }]
        })
        .state('taxpayer-account.edit', {
            parent: 'taxpayer-account',
            url: '/{id}/edit',
            data: {
                authorities: []
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taxpayer-account/taxpayer-account-dialog.html',
                    controller: 'Taxpayer_accountDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Taxpayer_account', function(Taxpayer_account) {
                            return Taxpayer_account.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('taxpayer-account', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('taxpayer-account.delete', {
            parent: 'taxpayer-account',
            url: '/{id}/delete',
            data: {
                authorities: []
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taxpayer-account/taxpayer-account-delete-dialog.html',
                    controller: 'Taxpayer_accountDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Taxpayer_account', function(Taxpayer_account) {
                            return Taxpayer_account.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('taxpayer-account', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
