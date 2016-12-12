(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-data-enajenante', {
            parent: 'entity',
            url: '/freecom-data-enajenante?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_data_enajenante.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-data-enajenante/freecom-data-enajenantes.html',
                    controller: 'Freecom_data_enajenanteController',
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
                    $translatePartialLoader.addPart('freecom_data_enajenante');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-data-enajenante-detail', {
            parent: 'entity',
            url: '/freecom-data-enajenante/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_data_enajenante.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-data-enajenante/freecom-data-enajenante-detail.html',
                    controller: 'Freecom_data_enajenanteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_data_enajenante');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_data_enajenante', function($stateParams, Freecom_data_enajenante) {
                    return Freecom_data_enajenante.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-data-enajenante.new', {
            parent: 'freecom-data-enajenante',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-data-enajenante/freecom-data-enajenante-dialog.html',
                    controller: 'Freecom_data_enajenanteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                coprosocconyugaie: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-data-enajenante', null, { reload: true });
                }, function() {
                    $state.go('freecom-data-enajenante');
                });
            }]
        })
        .state('freecom-data-enajenante.edit', {
            parent: 'freecom-data-enajenante',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-data-enajenante/freecom-data-enajenante-dialog.html',
                    controller: 'Freecom_data_enajenanteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_data_enajenante', function(Freecom_data_enajenante) {
                            return Freecom_data_enajenante.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-data-enajenante', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-data-enajenante.delete', {
            parent: 'freecom-data-enajenante',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-data-enajenante/freecom-data-enajenante-delete-dialog.html',
                    controller: 'Freecom_data_enajenanteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_data_enajenante', function(Freecom_data_enajenante) {
                            return Freecom_data_enajenante.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-data-enajenante', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
