(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-pfic', {
            parent: 'entity',
            url: '/com-pfic?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_pfic.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-pfic/com-pfics.html',
                    controller: 'Com_pficController',
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
                    $translatePartialLoader.addPart('com_pfic');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-pfic-detail', {
            parent: 'entity',
            url: '/com-pfic/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_pfic.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-pfic/com-pfic-detail.html',
                    controller: 'Com_pficDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_pfic');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_pfic', function($stateParams, Com_pfic) {
                    return Com_pfic.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-pfic.new', {
            parent: 'com-pfic',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-pfic/com-pfic-dialog.html',
                    controller: 'Com_pficDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                version: null,
                                key_vehicle: null,
                                license_plate: null,
                                rfcpf: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-pfic', null, { reload: true });
                }, function() {
                    $state.go('com-pfic');
                });
            }]
        })
        .state('com-pfic.edit', {
            parent: 'com-pfic',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-pfic/com-pfic-dialog.html',
                    controller: 'Com_pficDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_pfic', function(Com_pfic) {
                            return Com_pfic.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-pfic', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-pfic.delete', {
            parent: 'com-pfic',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-pfic/com-pfic-delete-dialog.html',
                    controller: 'Com_pficDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_pfic', function(Com_pfic) {
                            return Com_pfic.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-pfic', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
