(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-data-enajenante', {
            parent: 'entity',
            url: '/com-data-enajenante?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_data_enajenante.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-data-enajenante/com-data-enajenantes.html',
                    controller: 'Com_data_enajenanteController',
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
                    $translatePartialLoader.addPart('com_data_enajenante');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-data-enajenante-detail', {
            parent: 'entity',
            url: '/com-data-enajenante/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_data_enajenante.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-data-enajenante/com-data-enajenante-detail.html',
                    controller: 'Com_data_enajenanteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_data_enajenante');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_data_enajenante', function($stateParams, Com_data_enajenante) {
                    return Com_data_enajenante.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-data-enajenante.new', {
            parent: 'com-data-enajenante',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-data-enajenante/com-data-enajenante-dialog.html',
                    controller: 'Com_data_enajenanteDialogController',
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
                    $state.go('com-data-enajenante', null, { reload: true });
                }, function() {
                    $state.go('com-data-enajenante');
                });
            }]
        })
        .state('com-data-enajenante.edit', {
            parent: 'com-data-enajenante',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-data-enajenante/com-data-enajenante-dialog.html',
                    controller: 'Com_data_enajenanteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_data_enajenante', function(Com_data_enajenante) {
                            return Com_data_enajenante.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-data-enajenante', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-data-enajenante.delete', {
            parent: 'com-data-enajenante',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-data-enajenante/com-data-enajenante-delete-dialog.html',
                    controller: 'Com_data_enajenanteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_data_enajenante', function(Com_data_enajenante) {
                            return Com_data_enajenante.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-data-enajenante', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
