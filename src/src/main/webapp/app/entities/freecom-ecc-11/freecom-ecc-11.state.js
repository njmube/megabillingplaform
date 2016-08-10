(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-ecc-11', {
            parent: 'entity',
            url: '/freecom-ecc-11?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_ecc11.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-ecc-11/freecom-ecc-11-s.html',
                    controller: 'Freecom_ecc11Controller',
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
                    $translatePartialLoader.addPart('freecom_ecc11');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-ecc-11-detail', {
            parent: 'entity',
            url: '/freecom-ecc-11/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_ecc11.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-ecc-11/freecom-ecc-11-detail.html',
                    controller: 'Freecom_ecc11DetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_ecc11');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_ecc11', function($stateParams, Freecom_ecc11) {
                    return Freecom_ecc11.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('freecom-ecc-11.new', {
            parent: 'freecom-ecc-11',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-ecc-11/freecom-ecc-11-dialog.html',
                    controller: 'Freecom_ecc11DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                version: null,
                                type_operation: null,
                                number_account: null,
                                subtotal: null,
                                total: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-ecc-11', null, { reload: true });
                }, function() {
                    $state.go('freecom-ecc-11');
                });
            }]
        })
        .state('freecom-ecc-11.edit', {
            parent: 'freecom-ecc-11',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-ecc-11/freecom-ecc-11-dialog.html',
                    controller: 'Freecom_ecc11DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_ecc11', function(Freecom_ecc11) {
                            return Freecom_ecc11.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-ecc-11', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-ecc-11.delete', {
            parent: 'freecom-ecc-11',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-ecc-11/freecom-ecc-11-delete-dialog.html',
                    controller: 'Freecom_ecc11DeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_ecc11', function(Freecom_ecc11) {
                            return Freecom_ecc11.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-ecc-11', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
