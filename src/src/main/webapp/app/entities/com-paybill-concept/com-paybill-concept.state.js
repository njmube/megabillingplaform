(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-paybill-concept', {
            parent: 'entity',
            url: '/com-paybill-concept?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_paybill_concept.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-paybill-concept/com-paybill-concepts.html',
                    controller: 'Com_paybill_conceptController',
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
                    $translatePartialLoader.addPart('com_paybill_concept');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-paybill-concept-detail', {
            parent: 'entity',
            url: '/com-paybill-concept/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_paybill_concept.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-paybill-concept/com-paybill-concept-detail.html',
                    controller: 'Com_paybill_conceptDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_paybill_concept');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_paybill_concept', function($stateParams, Com_paybill_concept) {
                    return Com_paybill_concept.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-paybill-concept.new', {
            parent: 'com-paybill-concept',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-paybill-concept/com-paybill-concept-dialog.html',
                    controller: 'Com_paybill_conceptDialogController',
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
                    $state.go('com-paybill-concept', null, { reload: true });
                }, function() {
                    $state.go('com-paybill-concept');
                });
            }]
        })
        .state('com-paybill-concept.edit', {
            parent: 'com-paybill-concept',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-paybill-concept/com-paybill-concept-dialog.html',
                    controller: 'Com_paybill_conceptDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_paybill_concept', function(Com_paybill_concept) {
                            return Com_paybill_concept.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-paybill-concept', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-paybill-concept.delete', {
            parent: 'com-paybill-concept',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-paybill-concept/com-paybill-concept-delete-dialog.html',
                    controller: 'Com_paybill_conceptDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_paybill_concept', function(Com_paybill_concept) {
                            return Com_paybill_concept.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-paybill-concept', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
