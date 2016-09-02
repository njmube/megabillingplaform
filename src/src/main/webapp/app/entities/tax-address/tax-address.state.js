(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tax-address', {
            parent: 'entity',
            url: '/tax-address?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.tax_address.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tax-address/tax-addresses.html',
                    controller: 'Tax_addressController',
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
                    $translatePartialLoader.addPart('tax_address');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tax-address-detail', {
            parent: 'entity',
            url: '/tax-address/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.tax_address.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tax-address/tax-address-detail.html',
                    controller: 'Tax_addressDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tax_address');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tax_address', function($stateParams, Tax_address) {
                    return Tax_address.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('tax-address.new', {
            parent: 'tax-address',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tax-address/tax-address-dialog.html',
                    controller: 'Tax_addressDialogController',
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
                    $state.go('tax-address', null, { reload: true });
                }, function() {
                    $state.go('tax-address');
                });
            }]
        })
        .state('tax-address.edit', {
            parent: 'tax-address',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tax-address/tax-address-dialog.html',
                    controller: 'Tax_addressDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tax_address', function(Tax_address) {
                            return Tax_address.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tax-address', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tax-address.delete', {
            parent: 'tax-address',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tax-address/tax-address-delete-dialog.html',
                    controller: 'Tax_addressDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tax_address', function(Tax_address) {
                            return Tax_address.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tax-address', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
