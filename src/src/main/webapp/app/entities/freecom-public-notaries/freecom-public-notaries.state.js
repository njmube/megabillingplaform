(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-public-notaries', {
            parent: 'entity',
            url: '/freecom-public-notaries?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_public_notaries.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-public-notaries/freecom-public-notaries.html',
                    controller: 'Freecom_public_notariesController',
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
                    $translatePartialLoader.addPart('freecom_public_notaries');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-public-notaries-detail', {
            parent: 'entity',
            url: '/freecom-public-notaries/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_public_notaries.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-public-notaries/freecom-public-notaries-detail.html',
                    controller: 'Freecom_public_notariesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_public_notaries');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_public_notaries', function($stateParams, Freecom_public_notaries) {
                    return Freecom_public_notaries.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-public-notaries.new', {
            parent: 'freecom-public-notaries',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-public-notaries/freecom-public-notaries-dialog.html',
                    controller: 'Freecom_public_notariesDialogController',
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
                    $state.go('freecom-public-notaries', null, { reload: true });
                }, function() {
                    $state.go('freecom-public-notaries');
                });
            }]
        })
        .state('freecom-public-notaries.edit', {
            parent: 'freecom-public-notaries',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-public-notaries/freecom-public-notaries-dialog.html',
                    controller: 'Freecom_public_notariesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_public_notaries', function(Freecom_public_notaries) {
                            return Freecom_public_notaries.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-public-notaries', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-public-notaries.delete', {
            parent: 'freecom-public-notaries',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-public-notaries/freecom-public-notaries-delete-dialog.html',
                    controller: 'Freecom_public_notariesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_public_notaries', function(Freecom_public_notaries) {
                            return Freecom_public_notaries.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-public-notaries', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
