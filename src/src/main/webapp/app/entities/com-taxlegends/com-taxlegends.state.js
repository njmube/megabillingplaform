(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-taxlegends', {
            parent: 'entity',
            url: '/com-taxlegends?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_taxlegends.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-taxlegends/com-taxlegends.html',
                    controller: 'Com_taxlegendsController',
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
                    $translatePartialLoader.addPart('com_taxlegends');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-taxlegends-detail', {
            parent: 'entity',
            url: '/com-taxlegends/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_taxlegends.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-taxlegends/com-taxlegends-detail.html',
                    controller: 'Com_taxlegendsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_taxlegends');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_taxlegends', function($stateParams, Com_taxlegends) {
                    return Com_taxlegends.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-taxlegends.new', {
            parent: 'com-taxlegends',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-taxlegends/com-taxlegends-dialog.html',
                    controller: 'Com_taxlegendsDialogController',
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
                    $state.go('com-taxlegends', null, { reload: true });
                }, function() {
                    $state.go('com-taxlegends');
                });
            }]
        })
        .state('com-taxlegends.edit', {
            parent: 'com-taxlegends',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-taxlegends/com-taxlegends-dialog.html',
                    controller: 'Com_taxlegendsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_taxlegends', function(Com_taxlegends) {
                            return Com_taxlegends.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-taxlegends', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-taxlegends.delete', {
            parent: 'com-taxlegends',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-taxlegends/com-taxlegends-delete-dialog.html',
                    controller: 'Com_taxlegendsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_taxlegends', function(Com_taxlegends) {
                            return Com_taxlegends.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-taxlegends', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
