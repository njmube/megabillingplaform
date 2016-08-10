(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-spei', {
            parent: 'entity',
            url: '/freecom-spei?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_spei.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-spei/freecom-speis.html',
                    controller: 'Freecom_speiController',
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
                    $translatePartialLoader.addPart('freecom_spei');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-spei-detail', {
            parent: 'entity',
            url: '/freecom-spei/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_spei.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-spei/freecom-spei-detail.html',
                    controller: 'Freecom_speiDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_spei');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_spei', function($stateParams, Freecom_spei) {
                    return Freecom_spei.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('freecom-spei.new', {
            parent: 'freecom-spei',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-spei/freecom-spei-dialog.html',
                    controller: 'Freecom_speiDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-spei', null, { reload: true });
                }, function() {
                    $state.go('freecom-spei');
                });
            }]
        })
        .state('freecom-spei.edit', {
            parent: 'freecom-spei',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-spei/freecom-spei-dialog.html',
                    controller: 'Freecom_speiDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_spei', function(Freecom_spei) {
                            return Freecom_spei.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-spei', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-spei.delete', {
            parent: 'freecom-spei',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-spei/freecom-spei-delete-dialog.html',
                    controller: 'Freecom_speiDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_spei', function(Freecom_spei) {
                            return Freecom_spei.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-spei', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
