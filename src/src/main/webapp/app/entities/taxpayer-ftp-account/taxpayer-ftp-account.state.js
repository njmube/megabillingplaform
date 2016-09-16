(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('taxpayer-ftp-account', {
            parent: 'entity',
            url: '/taxpayer-ftp-account?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.taxpayer_ftp_account.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/taxpayer-ftp-account/taxpayer-ftp-accounts.html',
                    controller: 'Taxpayer_ftp_accountController',
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
                    $translatePartialLoader.addPart('taxpayer_ftp_account');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('taxpayer-ftp-account-detail', {
            parent: 'entity',
            url: '/taxpayer-ftp-account/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.taxpayer_ftp_account.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/taxpayer-ftp-account/taxpayer-ftp-account-detail.html',
                    controller: 'Taxpayer_ftp_accountDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('taxpayer_ftp_account');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Taxpayer_ftp_account', function($stateParams, Taxpayer_ftp_account) {
                    return Taxpayer_ftp_account.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('taxpayer-ftp-account.new', {
            parent: 'taxpayer-ftp-account',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taxpayer-ftp-account/taxpayer-ftp-account-dialog.html',
                    controller: 'Taxpayer_ftp_accountDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                server_type: null,
                                server_name_ip: null,
                                port: null,
                                username: null,
                                password: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('taxpayer-ftp-account', null, { reload: true });
                }, function() {
                    $state.go('taxpayer-ftp-account');
                });
            }]
        })
        .state('taxpayer-ftp-account.edit', {
            parent: 'taxpayer-ftp-account',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taxpayer-ftp-account/taxpayer-ftp-account-dialog.html',
                    controller: 'Taxpayer_ftp_accountDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Taxpayer_ftp_account', function(Taxpayer_ftp_account) {
                            return Taxpayer_ftp_account.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('taxpayer-ftp-account', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('taxpayer-ftp-account.delete', {
            parent: 'taxpayer-ftp-account',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taxpayer-ftp-account/taxpayer-ftp-account-delete-dialog.html',
                    controller: 'Taxpayer_ftp_accountDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Taxpayer_ftp_account', function(Taxpayer_ftp_account) {
                            return Taxpayer_ftp_account.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('taxpayer-ftp-account', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
