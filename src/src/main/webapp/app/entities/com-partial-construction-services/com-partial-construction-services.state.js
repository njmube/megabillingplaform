(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-partial-construction-services', {
            parent: 'entity',
            url: '/com-partial-construction-services?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_partial_construction_services.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-partial-construction-services/com-partial-construction-services.html',
                    controller: 'Com_partial_construction_servicesController',
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
                    $translatePartialLoader.addPart('com_partial_construction_services');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-partial-construction-services-detail', {
            parent: 'entity',
            url: '/com-partial-construction-services/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_partial_construction_services.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-partial-construction-services/com-partial-construction-services-detail.html',
                    controller: 'Com_partial_construction_servicesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_partial_construction_services');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_partial_construction_services', function($stateParams, Com_partial_construction_services) {
                    return Com_partial_construction_services.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-partial-construction-services.new', {
            parent: 'com-partial-construction-services',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-partial-construction-services/com-partial-construction-services-dialog.html',
                    controller: 'Com_partial_construction_servicesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                version: null,
                                street: null,
                                noext: null,
                                noint: null,
                                location: null,
                                reference: null,
                                numperlicoaut: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-partial-construction-services', null, { reload: true });
                }, function() {
                    $state.go('com-partial-construction-services');
                });
            }]
        })
        .state('com-partial-construction-services.edit', {
            parent: 'com-partial-construction-services',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-partial-construction-services/com-partial-construction-services-dialog.html',
                    controller: 'Com_partial_construction_servicesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_partial_construction_services', function(Com_partial_construction_services) {
                            return Com_partial_construction_services.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-partial-construction-services', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-partial-construction-services.delete', {
            parent: 'com-partial-construction-services',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-partial-construction-services/com-partial-construction-services-delete-dialog.html',
                    controller: 'Com_partial_construction_servicesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_partial_construction_services', function(Com_partial_construction_services) {
                            return Com_partial_construction_services.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-partial-construction-services', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
