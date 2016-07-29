(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-partial-construction-services', {
            parent: 'entity',
            url: '/freecom-partial-construction-services?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_partial_construction_services.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-partial-construction-services/freecom-partial-construction-services.html',
                    controller: 'Freecom_partial_construction_servicesController',
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
                    $translatePartialLoader.addPart('freecom_partial_construction_services');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-partial-construction-services-detail', {
            parent: 'entity',
            url: '/freecom-partial-construction-services/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_partial_construction_services.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-partial-construction-services/freecom-partial-construction-services-detail.html',
                    controller: 'Freecom_partial_construction_servicesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_partial_construction_services');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_partial_construction_services', function($stateParams, Freecom_partial_construction_services) {
                    return Freecom_partial_construction_services.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-partial-construction-services.new', {
            parent: 'freecom-partial-construction-services',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-partial-construction-services/freecom-partial-construction-services-dialog.html',
                    controller: 'Freecom_partial_construction_servicesDialogController',
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
                                colony: null,
                                location: null,
                                reference: null,
                                municipality: null,
                                zipcode: null,
                                numperlicoaut: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-partial-construction-services', null, { reload: true });
                }, function() {
                    $state.go('freecom-partial-construction-services');
                });
            }]
        })
        .state('freecom-partial-construction-services.edit', {
            parent: 'freecom-partial-construction-services',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-partial-construction-services/freecom-partial-construction-services-dialog.html',
                    controller: 'Freecom_partial_construction_servicesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_partial_construction_services', function(Freecom_partial_construction_services) {
                            return Freecom_partial_construction_services.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-partial-construction-services', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-partial-construction-services.delete', {
            parent: 'freecom-partial-construction-services',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-partial-construction-services/freecom-partial-construction-services-delete-dialog.html',
                    controller: 'Freecom_partial_construction_servicesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_partial_construction_services', function(Freecom_partial_construction_services) {
                            return Freecom_partial_construction_services.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-partial-construction-services', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
