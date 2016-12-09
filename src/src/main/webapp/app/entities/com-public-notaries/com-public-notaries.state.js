(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-public-notaries', {
            parent: 'entity',
            url: '/com-public-notaries?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_public_notaries.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-public-notaries/com-public-notaries.html',
                    controller: 'Com_public_notariesController',
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
                    $translatePartialLoader.addPart('com_public_notaries');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-public-notaries-detail', {
            parent: 'entity',
            url: '/com-public-notaries/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_public_notaries.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-public-notaries/com-public-notaries-detail.html',
                    controller: 'Com_public_notariesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_public_notaries');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_public_notaries', function($stateParams, Com_public_notaries) {
                    return Com_public_notaries.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-public-notaries.new', {
            parent: 'com-public-notaries',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-public-notaries/com-public-notaries-dialog.html',
                    controller: 'Com_public_notariesDialogController',
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
                    $state.go('com-public-notaries', null, { reload: true });
                }, function() {
                    $state.go('com-public-notaries');
                });
            }]
        })
        .state('com-public-notaries.edit', {
            parent: 'com-public-notaries',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-public-notaries/com-public-notaries-dialog.html',
                    controller: 'Com_public_notariesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_public_notaries', function(Com_public_notaries) {
                            return Com_public_notaries.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-public-notaries', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-public-notaries.delete', {
            parent: 'com-public-notaries',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-public-notaries/com-public-notaries-delete-dialog.html',
                    controller: 'Com_public_notariesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_public_notaries', function(Com_public_notaries) {
                            return Com_public_notaries.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-public-notaries', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
