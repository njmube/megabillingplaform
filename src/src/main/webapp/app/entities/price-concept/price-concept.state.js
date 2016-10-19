(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('price-concept', {
            parent: 'entity',
            url: '/price-concept',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.price_concept.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/price-concept/price-concepts.html',
                    controller: 'Price_conceptController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('price_concept');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('price-concept-detail', {
            parent: 'entity',
            url: '/price-concept/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.price_concept.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/price-concept/price-concept-detail.html',
                    controller: 'Price_conceptDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('price_concept');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Price_concept', function($stateParams, Price_concept) {
                    return Price_concept.get({id : $stateParams.id});
                }]
            }
        })
        .state('price-concept.new', {
            parent: 'price-concept',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/price-concept/price-concept-dialog.html',
                    controller: 'Price_conceptDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                value: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('price-concept', null, { reload: true });
                }, function() {
                    $state.go('price-concept');
                });
            }]
        })
        .state('price-concept.edit', {
            parent: 'price-concept',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/price-concept/price-concept-dialog.html',
                    controller: 'Price_conceptDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Price_concept', function(Price_concept) {
                            return Price_concept.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('price-concept', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('price-concept.delete', {
            parent: 'price-concept',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/price-concept/price-concept-delete-dialog.html',
                    controller: 'Price_conceptDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Price_concept', function(Price_concept) {
                            return Price_concept.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('price-concept', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
