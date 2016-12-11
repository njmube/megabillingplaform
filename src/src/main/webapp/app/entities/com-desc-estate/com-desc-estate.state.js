(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-desc-estate', {
            parent: 'entity',
            url: '/com-desc-estate?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_desc_estate.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-desc-estate/com-desc-estates.html',
                    controller: 'Com_desc_estateController',
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
                    $translatePartialLoader.addPart('com_desc_estate');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-desc-estate-detail', {
            parent: 'entity',
            url: '/com-desc-estate/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_desc_estate.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-desc-estate/com-desc-estate-detail.html',
                    controller: 'Com_desc_estateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_desc_estate');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_desc_estate', function($stateParams, Com_desc_estate) {
                    return Com_desc_estate.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-desc-estate.new', {
            parent: 'com-desc-estate',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-desc-estate/com-desc-estate-dialog.html',
                    controller: 'Com_desc_estateDialogController',
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
                    $state.go('com-desc-estate', null, { reload: true });
                }, function() {
                    $state.go('com-desc-estate');
                });
            }]
        })
        .state('com-desc-estate.edit', {
            parent: 'com-desc-estate',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-desc-estate/com-desc-estate-dialog.html',
                    controller: 'Com_desc_estateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_desc_estate', function(Com_desc_estate) {
                            return Com_desc_estate.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-desc-estate', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-desc-estate.delete', {
            parent: 'com-desc-estate',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-desc-estate/com-desc-estate-delete-dialog.html',
                    controller: 'Com_desc_estateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_desc_estate', function(Com_desc_estate) {
                            return Com_desc_estate.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-desc-estate', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
