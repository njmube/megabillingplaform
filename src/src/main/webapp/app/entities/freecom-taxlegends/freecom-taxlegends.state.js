(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-taxlegends', {
            parent: 'entity',
            url: '/freecom-taxlegends?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_taxlegends.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-taxlegends/freecom-taxlegends.html',
                    controller: 'Freecom_taxlegendsController',
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
                    $translatePartialLoader.addPart('freecom_taxlegends');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-taxlegends-detail', {
            parent: 'entity',
            url: '/freecom-taxlegends/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_taxlegends.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-taxlegends/freecom-taxlegends-detail.html',
                    controller: 'Freecom_taxlegendsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_taxlegends');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_taxlegends', function($stateParams, Freecom_taxlegends) {
                    return Freecom_taxlegends.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-taxlegends.new', {
            parent: 'freecom-taxlegends',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-taxlegends/freecom-taxlegends-dialog.html',
                    controller: 'Freecom_taxlegendsDialogController',
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
                    $state.go('freecom-taxlegends', null, { reload: true });
                }, function() {
                    $state.go('freecom-taxlegends');
                });
            }]
        })
        .state('freecom-taxlegends.edit', {
            parent: 'freecom-taxlegends',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-taxlegends/freecom-taxlegends-dialog.html',
                    controller: 'Freecom_taxlegendsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_taxlegends', function(Freecom_taxlegends) {
                            return Freecom_taxlegends.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-taxlegends', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-taxlegends.delete', {
            parent: 'freecom-taxlegends',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-taxlegends/freecom-taxlegends-delete-dialog.html',
                    controller: 'Freecom_taxlegendsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_taxlegends', function(Freecom_taxlegends) {
                            return Freecom_taxlegends.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-taxlegends', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
