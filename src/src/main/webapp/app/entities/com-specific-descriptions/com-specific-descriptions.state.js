(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-specific-descriptions', {
            parent: 'entity',
            url: '/com-specific-descriptions?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_specific_descriptions.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-specific-descriptions/com-specific-descriptions.html',
                    controller: 'Com_specific_descriptionsController',
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
                    $translatePartialLoader.addPart('com_specific_descriptions');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-specific-descriptions-detail', {
            parent: 'entity',
            url: '/com-specific-descriptions/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_specific_descriptions.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-specific-descriptions/com-specific-descriptions-detail.html',
                    controller: 'Com_specific_descriptionsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_specific_descriptions');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_specific_descriptions', function($stateParams, Com_specific_descriptions) {
                    return Com_specific_descriptions.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-specific-descriptions.new', {
            parent: 'com-specific-descriptions',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-specific-descriptions/com-specific-descriptions-dialog.html',
                    controller: 'Com_specific_descriptionsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                brand: null,
                                model: null,
                                submodel: null,
                                serial_number: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-specific-descriptions', null, { reload: true });
                }, function() {
                    $state.go('com-specific-descriptions');
                });
            }]
        })
        .state('com-specific-descriptions.edit', {
            parent: 'com-specific-descriptions',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-specific-descriptions/com-specific-descriptions-dialog.html',
                    controller: 'Com_specific_descriptionsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_specific_descriptions', function(Com_specific_descriptions) {
                            return Com_specific_descriptions.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-specific-descriptions', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-specific-descriptions.delete', {
            parent: 'com-specific-descriptions',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-specific-descriptions/com-specific-descriptions-delete-dialog.html',
                    controller: 'Com_specific_descriptionsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_specific_descriptions', function(Com_specific_descriptions) {
                            return Com_specific_descriptions.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-specific-descriptions', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
