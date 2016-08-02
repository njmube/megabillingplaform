(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tracemg', {
            parent: 'entity',
            url: '/tracemg?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.tracemg.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tracemg/tracemgs.html',
                    controller: 'TracemgController',
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
                    $translatePartialLoader.addPart('tracemg');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tracemg-detail', {
            parent: 'entity',
            url: '/tracemg/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.tracemg.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tracemg/tracemg-detail.html',
                    controller: 'TracemgDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tracemg');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tracemg', function($stateParams, Tracemg) {
                    return Tracemg.get({id : $stateParams.id});
                }]
            }
        })
        .state('tracemg.new', {
            parent: 'tracemg',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tracemg/tracemg-dialog.html',
                    controller: 'TracemgDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                principal: null,
                                timestamp: null,
                                ip: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tracemg', null, { reload: true });
                }, function() {
                    $state.go('tracemg');
                });
            }]
        })
        .state('tracemg.edit', {
            parent: 'tracemg',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tracemg/tracemg-dialog.html',
                    controller: 'TracemgDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tracemg', function(Tracemg) {
                            return Tracemg.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('tracemg', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tracemg.delete', {
            parent: 'tracemg',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tracemg/tracemg-delete-dialog.html',
                    controller: 'TracemgDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tracemg', function(Tracemg) {
                            return Tracemg.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('tracemg', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
