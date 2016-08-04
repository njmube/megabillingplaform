(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-paybill-concept', {
            parent: 'entity',
            url: '/freecom-paybill-concept?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_paybill_concept.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-paybill-concept/freecom-paybill-concepts.html',
                    controller: 'Freecom_paybill_conceptController',
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
                    $translatePartialLoader.addPart('freecom_paybill_concept');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-paybill-concept-detail', {
            parent: 'entity',
            url: '/freecom-paybill-concept/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_paybill_concept.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-paybill-concept/freecom-paybill-concept-detail.html',
                    controller: 'Freecom_paybill_conceptDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_paybill_concept');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_paybill_concept', function($stateParams, Freecom_paybill_concept) {
                    return Freecom_paybill_concept.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-paybill-concept.new', {
            parent: 'freecom-paybill-concept',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-paybill-concept/freecom-paybill-concept-dialog.html',
                    controller: 'Freecom_paybill_conceptDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                identification_number: null,
                                date_expedition: null,
                                rfc: null,
                                curp: null,
                                name: null,
                                social_security_number: null,
                                amount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-paybill-concept', null, { reload: true });
                }, function() {
                    $state.go('freecom-paybill-concept');
                });
            }]
        })
        .state('freecom-paybill-concept.edit', {
            parent: 'freecom-paybill-concept',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-paybill-concept/freecom-paybill-concept-dialog.html',
                    controller: 'Freecom_paybill_conceptDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_paybill_concept', function(Freecom_paybill_concept) {
                            return Freecom_paybill_concept.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-paybill-concept', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-paybill-concept.delete', {
            parent: 'freecom-paybill-concept',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-paybill-concept/freecom-paybill-concept-delete-dialog.html',
                    controller: 'Freecom_paybill_conceptDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_paybill_concept', function(Freecom_paybill_concept) {
                            return Freecom_paybill_concept.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-paybill-concept', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
