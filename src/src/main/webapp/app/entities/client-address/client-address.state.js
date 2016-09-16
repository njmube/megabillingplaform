(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('client-address', {
            parent: 'entity',
            url: '/client-address?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.client_address.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/client-address/client-addresses.html',
                    controller: 'Client_addressController',
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
                    $translatePartialLoader.addPart('client_address');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('client-address-detail', {
            parent: 'entity',
            url: '/client-address/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.client_address.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/client-address/client-address-detail.html',
                    controller: 'Client_addressDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('client_address');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Client_address', function($stateParams, Client_address) {
                    return Client_address.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('client-address.new', {
            parent: 'client-address',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/client-address/client-address-dialog.html',
                    controller: 'Client_addressDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                street: null,
                                no_int: null,
                                no_ext: null,
                                location: null,
                                intersection: null,
                                reference: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('client-address', null, { reload: true });
                }, function() {
                    $state.go('client-address');
                });
            }]
        })
        .state('client-address.edit', {
            parent: 'client-address',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/client-address/client-address-dialog.html',
                    controller: 'Client_addressDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Client_address', function(Client_address) {
                            return Client_address.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('client-address', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('client-address.delete', {
            parent: 'client-address',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/client-address/client-address-delete-dialog.html',
                    controller: 'Client_addressDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Client_address', function(Client_address) {
                            return Client_address.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('client-address', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
