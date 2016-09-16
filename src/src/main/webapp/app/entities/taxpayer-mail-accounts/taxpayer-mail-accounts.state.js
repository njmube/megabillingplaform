(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('taxpayer-mail-accounts', {
            parent: 'entity',
            url: '/taxpayer-mail-accounts?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.taxpayer_mail_accounts.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/taxpayer-mail-accounts/taxpayer-mail-accounts.html',
                    controller: 'Taxpayer_mail_accountsController',
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
                    $translatePartialLoader.addPart('taxpayer_mail_accounts');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('taxpayer-mail-accounts-detail', {
            parent: 'entity',
            url: '/taxpayer-mail-accounts/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.taxpayer_mail_accounts.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/taxpayer-mail-accounts/taxpayer-mail-accounts-detail.html',
                    controller: 'Taxpayer_mail_accountsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('taxpayer_mail_accounts');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Taxpayer_mail_accounts', function($stateParams, Taxpayer_mail_accounts) {
                    return Taxpayer_mail_accounts.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('taxpayer-mail-accounts.new', {
            parent: 'taxpayer-mail-accounts',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taxpayer-mail-accounts/taxpayer-mail-accounts-dialog.html',
                    controller: 'Taxpayer_mail_accountsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                incoming_mail_type: null,
                                incoming_server_name: null,
                                incoming_port: null,
                                username: null,
                                password: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('taxpayer-mail-accounts', null, { reload: true });
                }, function() {
                    $state.go('taxpayer-mail-accounts');
                });
            }]
        })
        .state('taxpayer-mail-accounts.edit', {
            parent: 'taxpayer-mail-accounts',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taxpayer-mail-accounts/taxpayer-mail-accounts-dialog.html',
                    controller: 'Taxpayer_mail_accountsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Taxpayer_mail_accounts', function(Taxpayer_mail_accounts) {
                            return Taxpayer_mail_accounts.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('taxpayer-mail-accounts', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('taxpayer-mail-accounts.delete', {
            parent: 'taxpayer-mail-accounts',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taxpayer-mail-accounts/taxpayer-mail-accounts-delete-dialog.html',
                    controller: 'Taxpayer_mail_accountsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Taxpayer_mail_accounts', function(Taxpayer_mail_accounts) {
                            return Taxpayer_mail_accounts.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('taxpayer-mail-accounts', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
