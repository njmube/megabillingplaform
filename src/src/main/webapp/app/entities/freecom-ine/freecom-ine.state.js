(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-ine', {
            parent: 'entity',
            url: '/freecom-ine?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_ine.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-ine/freecom-ines.html',
                    controller: 'Freecom_ineController',
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
                    $translatePartialLoader.addPart('freecom_ine');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-ine-detail', {
            parent: 'entity',
            url: '/freecom-ine/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_ine.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-ine/freecom-ine-detail.html',
                    controller: 'Freecom_ineDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_ine');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_ine', function($stateParams, Freecom_ine) {
                    return Freecom_ine.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-ine.new', {
            parent: 'freecom-ine',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-ine/freecom-ine-dialog.html',
                    controller: 'Freecom_ineDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                version: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-ine', null, { reload: true });
                }, function() {
                    $state.go('freecom-ine');
                });
            }]
        })
        .state('freecom-ine.edit', {
            parent: 'freecom-ine',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-ine/freecom-ine-dialog.html',
                    controller: 'Freecom_ineDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_ine', function(Freecom_ine) {
                            return Freecom_ine.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-ine', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-ine.delete', {
            parent: 'freecom-ine',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-ine/freecom-ine-delete-dialog.html',
                    controller: 'Freecom_ineDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_ine', function(Freecom_ine) {
                            return Freecom_ine.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-ine', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
