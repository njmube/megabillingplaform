(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-tax-type', {
            parent: 'entity',
            url: '/freecom-tax-type?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_tax_type.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-tax-type/freecom-tax-types.html',
                    controller: 'Freecom_tax_typeController',
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
                    $translatePartialLoader.addPart('freecom_tax_type');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-tax-type-detail', {
            parent: 'entity',
            url: '/freecom-tax-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_tax_type.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-tax-type/freecom-tax-type-detail.html',
                    controller: 'Freecom_tax_typeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_tax_type');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_tax_type', function($stateParams, Freecom_tax_type) {
                    return Freecom_tax_type.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-tax-type.new', {
            parent: 'freecom-tax-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-tax-type/freecom-tax-type-dialog.html',
                    controller: 'Freecom_tax_typeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-tax-type', null, { reload: true });
                }, function() {
                    $state.go('freecom-tax-type');
                });
            }]
        })
        .state('freecom-tax-type.edit', {
            parent: 'freecom-tax-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-tax-type/freecom-tax-type-dialog.html',
                    controller: 'Freecom_tax_typeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_tax_type', function(Freecom_tax_type) {
                            return Freecom_tax_type.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-tax-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-tax-type.delete', {
            parent: 'freecom-tax-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-tax-type/freecom-tax-type-delete-dialog.html',
                    controller: 'Freecom_tax_typeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_tax_type', function(Freecom_tax_type) {
                            return Freecom_tax_type.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-tax-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
