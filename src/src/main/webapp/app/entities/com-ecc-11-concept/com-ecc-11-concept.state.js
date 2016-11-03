(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-ecc-11-concept', {
            parent: 'entity',
            url: '/com-ecc-11-concept?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_ecc_11_concept.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-ecc-11-concept/com-ecc-11-concepts.html',
                    controller: 'Com_ecc_11_conceptController',
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
                    $translatePartialLoader.addPart('com_ecc_11_concept');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-ecc-11-concept-detail', {
            parent: 'entity',
            url: '/com-ecc-11-concept/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_ecc_11_concept.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-ecc-11-concept/com-ecc-11-concept-detail.html',
                    controller: 'Com_ecc_11_conceptDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_ecc_11_concept');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_ecc_11_concept', function($stateParams, Com_ecc_11_concept) {
                    return Com_ecc_11_concept.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-ecc-11-concept.new', {
            parent: 'com-ecc-11-concept',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-ecc-11-concept/com-ecc-11-concept-dialog.html',
                    controller: 'Com_ecc_11_conceptDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                identifier: null,
                                date: null,
                                rfc: null,
                                key_station: null,
                                quantity: null,
                                unit: null,
                                fuel_name: null,
                                folio_operation: null,
                                unit_value: null,
                                amount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-ecc-11-concept', null, { reload: true });
                }, function() {
                    $state.go('com-ecc-11-concept');
                });
            }]
        })
        .state('com-ecc-11-concept.edit', {
            parent: 'com-ecc-11-concept',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-ecc-11-concept/com-ecc-11-concept-dialog.html',
                    controller: 'Com_ecc_11_conceptDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_ecc_11_concept', function(Com_ecc_11_concept) {
                            return Com_ecc_11_concept.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-ecc-11-concept', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-ecc-11-concept.delete', {
            parent: 'com-ecc-11-concept',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-ecc-11-concept/com-ecc-11-concept-delete-dialog.html',
                    controller: 'Com_ecc_11_conceptDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_ecc_11_concept', function(Com_ecc_11_concept) {
                            return Com_ecc_11_concept.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-ecc-11-concept', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
