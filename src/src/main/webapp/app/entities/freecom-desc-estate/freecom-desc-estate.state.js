(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-desc-estate', {
            parent: 'entity',
            url: '/freecom-desc-estate?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_desc_estate.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-desc-estate/freecom-desc-estates.html',
                    controller: 'Freecom_desc_estateController',
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
                    $translatePartialLoader.addPart('freecom_desc_estate');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-desc-estate-detail', {
            parent: 'entity',
            url: '/freecom-desc-estate/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_desc_estate.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-desc-estate/freecom-desc-estate-detail.html',
                    controller: 'Freecom_desc_estateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_desc_estate');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_desc_estate', function($stateParams, Freecom_desc_estate) {
                    return Freecom_desc_estate.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-desc-estate.new', {
            parent: 'freecom-desc-estate',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-desc-estate/freecom-desc-estate-dialog.html',
                    controller: 'Freecom_desc_estateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                street: null,
                                noext: null,
                                noint: null,
                                locale: null,
                                reference: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-desc-estate', null, { reload: true });
                }, function() {
                    $state.go('freecom-desc-estate');
                });
            }]
        })
        .state('freecom-desc-estate.edit', {
            parent: 'freecom-desc-estate',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-desc-estate/freecom-desc-estate-dialog.html',
                    controller: 'Freecom_desc_estateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_desc_estate', function(Freecom_desc_estate) {
                            return Freecom_desc_estate.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-desc-estate', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-desc-estate.delete', {
            parent: 'freecom-desc-estate',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-desc-estate/freecom-desc-estate-delete-dialog.html',
                    controller: 'Freecom_desc_estateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_desc_estate', function(Freecom_desc_estate) {
                            return Freecom_desc_estate.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-desc-estate', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
