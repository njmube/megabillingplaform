(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tax-address-request', {
            parent: 'entity',
            url: '/tax-address-request?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.tax_address_request.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tax-address-request/tax-address-requests.html',
                    controller: 'Tax_address_requestController',
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
                    $translatePartialLoader.addPart('tax_address_request');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tax-address-request-detail', {
            parent: 'entity',
            url: '/tax-address-request/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.tax_address_request.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tax-address-request/tax-address-request-detail.html',
                    controller: 'Tax_address_requestDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tax_address_request');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tax_address_request', function($stateParams, Tax_address_request) {
                    return Tax_address_request.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('tax-address-request.new', {
            parent: 'tax-address-request',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tax-address-request/tax-address-request-dialog.html',
                    controller: 'Tax_address_requestDialogController',
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
                    $state.go('tax-address-request', null, { reload: true });
                }, function() {
                    $state.go('tax-address-request');
                });
            }]
        })
        .state('tax-address-request.edit', {
            parent: 'tax-address-request',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tax-address-request/tax-address-request-dialog.html',
                    controller: 'Tax_address_requestDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tax_address_request', function(Tax_address_request) {
                            return Tax_address_request.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tax-address-request', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tax-address-request.delete', {
            parent: 'tax-address-request',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tax-address-request/tax-address-request-delete-dialog.html',
                    controller: 'Tax_address_requestDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tax_address_request', function(Tax_address_request) {
                            return Tax_address_request.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tax-address-request', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
